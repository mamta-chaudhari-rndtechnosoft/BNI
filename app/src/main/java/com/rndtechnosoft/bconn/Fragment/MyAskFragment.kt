package com.rndtechnosoft.bconn.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rndtechnosoft.bconn.Activity.AddMyAskActivity
import com.rndtechnosoft.bconn.Activity.AddMyGivesActivity
import com.rndtechnosoft.bconn.Adapter.MyAskAdapter
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.MyAskListData
import com.rndtechnosoft.bconn.Model.MyAskResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.FragmentMyAskBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MyAskFragment : Fragment() {

    private var _binding: FragmentMyAskBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MyAskAdapter
    private var userId: String? = null
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyAskBinding.inflate(inflater, container, false)

        userId = SaveSharedPreference.getInstance(requireContext()).getUserId()
        token = "bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add -> {
                    startActivity(Intent(requireContext(), AddMyAskActivity::class.java))
                    true
                }

                else -> false
            }
        }

        binding.rvMyAsks.layoutManager = LinearLayoutManager(requireContext())

        binding.layoutProgressBar.visibility = View.VISIBLE
        fetchMyAsk(token!!, userId!!)


        binding.swipeMyAsks.setOnRefreshListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            fetchMyAsk(token!!, userId!!)
            binding.swipeMyAsks.isRefreshing = false
        }


        return binding.root
    }

    private fun fetchMyAsk(token: String, userId: String) {

        RetrofitInstance.apiInterface.myAskList(token, userId)
            .enqueue(object : Callback<MyAskResponseData?> {
                override fun onResponse(
                    call: Call<MyAskResponseData?>,
                    response: Response<MyAskResponseData?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE
                        var myAskResponse = response.body()!!
                        var myAskList: MutableList<MyAskListData> =
                            myAskResponse.data as MutableList

                        adapter = MyAskAdapter(requireContext(), myAskList!!)
                        binding.rvMyAsks.adapter = adapter

                        if (adapter.itemCount == 0) {
                            binding.rvMyAsks.visibility = View.GONE
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.rvMyAsks.visibility = View.VISIBLE
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

                override fun onFailure(call: Call<MyAskResponseData?>, t: Throwable) {
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
        fetchMyAsk(token!!, userId!!)
    }

}