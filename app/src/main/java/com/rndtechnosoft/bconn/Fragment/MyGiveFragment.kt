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
import com.rndtechnosoft.bconn.Activity.AddMyGivesActivity
import com.rndtechnosoft.bconn.Adapter.MyGivesAdapter
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.MyGivesData
import com.rndtechnosoft.bconn.Model.MyGivesResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.FragmentMyGiveBinding
import com.rndtechnosoft.bconn.databinding.FragmentMyMatchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyGiveFragment : Fragment() {

    private var _binding:FragmentMyGiveBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: MyGivesAdapter
    var token: String? = null
    var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyGiveBinding.inflate(inflater,container,false)

        token = "bearer " + SaveSharedPreference.getInstance(requireContext()).getToken()
        userId = SaveSharedPreference.getInstance(requireContext()).getUserId()

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add -> {
                    startActivity(Intent(requireContext(), AddMyGivesActivity::class.java))
                    true
                }

                else -> false
            }
        }

        binding.rvMyGives.layoutManager = LinearLayoutManager(requireContext())

        binding.layoutProgressBar.visibility = View.VISIBLE
        getMyGives()

        binding.swipeMyGives.setOnRefreshListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            getMyGives()
            binding.swipeMyGives.isRefreshing = false
        }

        return binding.root
    }

    private fun getMyGives() {
        RetrofitInstance.apiInterface.getMyGives(token!!,userId!!).enqueue(object : Callback<MyGivesResponseData?> {
            override fun onResponse(
                call: Call<MyGivesResponseData?>,
                response: Response<MyGivesResponseData?>
            ) {
                 if(response.isSuccessful){
                     binding.layoutProgressBar.visibility = View.GONE
                     val myGivesResponse = response.body()
                     var myGivesList:MutableList<MyGivesData> = myGivesResponse!!.data as MutableList

                     adapter = MyGivesAdapter(myGivesList,requireContext())
                     binding.rvMyGives.adapter = adapter

                     if (adapter.itemCount == 0) {
                         binding.rvMyGives.visibility = View.GONE
                         binding.tvNoData.visibility = View.VISIBLE
                     } else {
                         binding.rvMyGives.visibility = View.VISIBLE
                         binding.tvNoData.visibility = View.GONE
                     }

                 }
                else{
                     binding.layoutProgressBar.visibility = View.GONE
                     Toast.makeText(
                         requireContext(),
                         "Response Error: ${response.code()}",
                         Toast.LENGTH_SHORT
                     ).show()
                     Log.d("Api Response", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MyGivesResponseData?>, t: Throwable) {
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
        getMyGives()
    }
}
