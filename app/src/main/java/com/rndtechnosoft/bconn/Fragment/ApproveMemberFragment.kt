package com.rndtechnosoft.bconn.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rndtechnosoft.bconn.Adapter.ApproveMemberAdapter
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.ReferralMembersResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.FragmentApproveMemberBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApproveMemberFragment : Fragment() {

    private var _binding: FragmentApproveMemberBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ApproveMemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApproveMemberBinding.inflate(inflater, container, false)


        binding.rvApproveMember.layoutManager = LinearLayoutManager(requireContext())

        binding.layoutProgressBar.visibility = View.VISIBLE
        getApprovedMember()


        binding.swipeRefresh.setOnRefreshListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            getApprovedMember()
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }

    private fun getApprovedMember() {

        val token = "bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()
        val refCode = SaveSharedPreference.getInstance(requireContext()).getReferralNumber()

        RetrofitInstance.apiInterface.getReferralMembers(token, refCode!!, "approved")
            .enqueue(object :
                Callback<ReferralMembersResponseData?> {
                override fun onResponse(
                    call: Call<ReferralMembersResponseData?>,
                    response: Response<ReferralMembersResponseData?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE

                        val referralMemberResponse = response.body()!!
                        val refMemberDataList = referralMemberResponse.data
                        adapter = ApproveMemberAdapter(requireContext(), refMemberDataList)
                        binding.rvApproveMember.adapter = adapter

                        if (adapter.itemCount == 0) {
                            binding.rvApproveMember.visibility = View.GONE
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.rvApproveMember.visibility = View.VISIBLE
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

}