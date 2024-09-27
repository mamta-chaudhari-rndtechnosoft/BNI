package com.rndtechnosoft.bconn.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rndtechnosoft.bconn.Adapter.PendingMemberAdapter
import com.rndtechnosoft.bconn.Adapter.RejectedMemberAdapter
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.MemberData
import com.rndtechnosoft.bconn.Model.ReferralMemberData
import com.rndtechnosoft.bconn.Model.ReferralMembersResponseData
import com.rndtechnosoft.bconn.Model.UpdateMemberStatusBody
import com.rndtechnosoft.bconn.Model.UpdateMemberStatusResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.FragmentPendingMemberBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PendingMemberFragment : Fragment(), PendingMemberAdapter.itemButtonClickListener {

    private var _binding: FragmentPendingMemberBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PendingMemberAdapter

    var refMemberDataList: MutableList<ReferralMemberData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPendingMemberBinding.inflate(inflater, container, false)

        binding.rvPendingMember.layoutManager = LinearLayoutManager(requireContext())

        binding.layoutProgressBar.visibility = View.VISIBLE
        getPendingMember()


        binding.swipeRefresh.setOnRefreshListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            getPendingMember()
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }


    private fun getPendingMember() {

        val token = "Bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()
        val refCode = SaveSharedPreference.getInstance(requireContext()).getReferralNumber()

        //Toast.makeText(requireContext(),"Code: " + refCode, Toast.LENGTH_SHORT).show()

        RetrofitInstance.apiInterface.getReferralMembers(token, refCode!!, "pending")
            .enqueue(object : Callback<ReferralMembersResponseData?> {
                override fun onResponse(
                    call: Call<ReferralMembersResponseData?>,
                    response: Response<ReferralMembersResponseData?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE

                        val referralMemberResponse = response.body()!!
                        //val refMemberDataList = referralMemberResponse.data as MutableList

                        //refMemberDataList.clear()
                        //refMemberDataList.addAll(referralMemberResponse.data)
                        refMemberDataList = referralMemberResponse.data as MutableList

                        adapter = PendingMemberAdapter(
                            requireContext(),
                            refMemberDataList,
                            this@PendingMemberFragment
                        )
                        binding.rvPendingMember.adapter = adapter

                        if (adapter.itemCount == 0) {
                            binding.rvPendingMember.visibility = View.GONE
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.rvPendingMember.visibility = View.VISIBLE
                            binding.tvNoData.visibility = View.GONE
                        }

                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Log.d("Api Response", "Response Error: ${response.code()}")
                        Toast.makeText(
                            requireContext(),
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ReferralMembersResponseData?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Log.d("Api Response", "Error: ${t.localizedMessage}")
                    Toast.makeText(
                        requireContext(),
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun approveMember(position: Int, id: String) {

        val updateMemberStatusBody = UpdateMemberStatusBody("approved")

        val token = "Bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()!!

        RetrofitInstance.apiInterface.updateMemberStatus(token, id, updateMemberStatusBody)
            .enqueue(object : Callback<UpdateMemberStatusResponseData?> {
                override fun onResponse(
                    call: Call<UpdateMemberStatusResponseData?>,
                    response: Response<UpdateMemberStatusResponseData?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE
                        val updateResponseData = response.body()!!
                        val message = updateResponseData.message

                        refMemberDataList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                        adapter.notifyItemRangeChanged(position, refMemberDataList.size)

                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Log.d("Api Response", "Response Error: ${response.code()}")
                        Toast.makeText(
                            requireContext(),
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UpdateMemberStatusResponseData?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Log.d("Api Response", "Error: ${t.localizedMessage}")
                    Toast.makeText(
                        requireContext(),
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun rejectMember(position: Int, id: String) {
        val updateMemberStatusBody = UpdateMemberStatusBody("cancel")

        val token = "Bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()!!

        RetrofitInstance.apiInterface.updateMemberStatus(token, id, updateMemberStatusBody)
            .enqueue(object : Callback<UpdateMemberStatusResponseData?> {
                override fun onResponse(
                    call: Call<UpdateMemberStatusResponseData?>,
                    response: Response<UpdateMemberStatusResponseData?>) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE
                        val updateResponseData = response.body()!!
                        val message = updateResponseData.message

                        refMemberDataList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                        adapter.notifyItemRangeChanged(position, refMemberDataList.size)

                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Log.d("Api Response", "Response Error: ${response.code()}")
                        Toast.makeText(
                            requireContext(),
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UpdateMemberStatusResponseData?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Log.d("Api Response", "Error: ${t.localizedMessage}")
                    Toast.makeText(
                        requireContext(),
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun approveMemberBtn(position: Int, id: String) {
        binding.layoutProgressBar.visibility = View.VISIBLE
        approveMember(position, id)
    }

    override fun rejectMemberBtn(position: Int, id: String) {
        binding.layoutProgressBar.visibility = View.VISIBLE
        rejectMember(position, id)
    }

   /* override fun onPause() {
        super.onPause()
        binding.layoutProgressBar.visibility = View.VISIBLE
        getPendingMember()
    }*/

    override fun onResume() {
        super.onResume()
        binding.layoutProgressBar.visibility = View.VISIBLE
        getPendingMember()
    }
}