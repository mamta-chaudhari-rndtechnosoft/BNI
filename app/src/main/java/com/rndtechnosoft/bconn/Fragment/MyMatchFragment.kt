package com.rndtechnosoft.bconn.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rndtechnosoft.bconn.Adapter.MyMatchAdapter
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.MatchedCompany
import com.rndtechnosoft.bconn.Model.MyAllMatchesResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.FragmentMyMatchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyMatchFragment : Fragment() {

    private var _binding: FragmentMyMatchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MyMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyMatchBinding.inflate(inflater, container, false)

        binding.rvMyMatch.layoutManager = LinearLayoutManager(requireContext())

        val token: String? = SaveSharedPreference.getInstance(requireContext()).getToken()
        val userId: String? = SaveSharedPreference.getInstance(requireContext()).getUserId()

        binding.layoutProgressBar.visibility = View.VISIBLE
        myAllMatchList()

        binding.swipeMyAsks.setOnRefreshListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            myAllMatchList()
            binding.swipeMyAsks.isRefreshing = false
        }

        return binding.root

    }

    private fun myAllMatchList() {

        val token: String? = SaveSharedPreference.getInstance(requireContext()).getToken()
        val userId: String? = SaveSharedPreference.getInstance(requireContext()).getUserId()

        RetrofitInstance.apiInterface.myAllMatches(token!!, userId!!)
            .enqueue(object : Callback<MyAllMatchesResponseData?> {
                override fun onResponse(
                    call: Call<MyAllMatchesResponseData?>,
                    response: Response<MyAllMatchesResponseData?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE
                        var myAllMatchResponse = response.body()
                        var myAllMatchList: MutableList<MatchedCompany> =
                            myAllMatchResponse!!.matchedCompanies as MutableList

                        adapter = MyMatchAdapter(requireContext(),myAllMatchList)
                        binding.rvMyMatch.adapter = adapter

                        if (adapter.itemCount == 0) {
                            binding.rvMyMatch.visibility = View.GONE
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.rvMyMatch.visibility = View.VISIBLE
                            binding.tvNoData.visibility = View.GONE
                        }

                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("Api Response", "Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MyAllMatchesResponseData?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("Api Response", "Error: ${t.localizedMessage}")
                }
            })

    }

    override fun onResume() {
        super.onResume()
        binding.layoutProgressBar.visibility = View.VISIBLE
        myAllMatchList()
    }
}