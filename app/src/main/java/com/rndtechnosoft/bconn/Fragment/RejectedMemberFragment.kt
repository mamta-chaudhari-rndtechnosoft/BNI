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
import com.rndtechnosoft.bconn.Adapter.MyAskAdapter
import com.rndtechnosoft.bconn.Adapter.RejectedMemberAdapter
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.ReferralMembersResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.FragmentRejectedMemberBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RejectedMemberFragment : Fragment() {

    private var _binding:FragmentRejectedMemberBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RejectedMemberAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRejectedMemberBinding.inflate(inflater,container,false)

        binding.rvRejectedMember.layoutManager = LinearLayoutManager(requireContext())

        binding.layoutProgressBar.visibility = View.VISIBLE
        getRejectedMember()


        binding.swipeRefresh.setOnRefreshListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            getRejectedMember()
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }

    private fun getRejectedMember(){

        val token = "bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()
        val refCode = SaveSharedPreference.getInstance(requireContext()).getReferralNumber()

        RetrofitInstance.apiInterface.getReferralMembers(token,refCode!!,"cancel").enqueue(object :
            Callback<ReferralMembersResponseData?> {
            override fun onResponse(
                call: Call<ReferralMembersResponseData?>,
                response: Response<ReferralMembersResponseData?>
            ) {
                if(response.isSuccessful){
                    binding.layoutProgressBar.visibility = View.GONE

                    val referralMemberResponse = response.body()!!
                    val refMemberDataList = referralMemberResponse.data
                    adapter = RejectedMemberAdapter(requireContext(), refMemberDataList)
                    binding.rvRejectedMember.adapter = adapter

                    if (adapter.itemCount == 0) {
                        binding.rvRejectedMember.visibility = View.GONE
                        binding.tvNoData.visibility = View.VISIBLE
                    } else {
                        binding.rvRejectedMember.visibility = View.VISIBLE
                        binding.tvNoData.visibility = View.GONE
                    }


                }
                else{
                    binding.layoutProgressBar.visibility = View.GONE
                    Log.d("Api Response","Response Error: ${response.code()}")
                    Toast.makeText(requireContext(),"Response Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ReferralMembersResponseData?>, t: Throwable) {
                binding.layoutProgressBar.visibility = View.GONE
                Log.d("Api Response","Error: ${t.localizedMessage}")
                Toast.makeText(requireContext(),"Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}