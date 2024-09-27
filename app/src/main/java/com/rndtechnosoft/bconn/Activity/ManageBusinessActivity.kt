package com.rndtechnosoft.bconn.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rndtechnosoft.bconn.Adapter.BusinessListAdapter
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.BusinessListResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityManageBusinessBinding
import com.rndtechnosoft.bconn.databinding.ActivityManageMembersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageBusinessActivity : AppCompatActivity() {

    private lateinit var binding:ActivityManageBusinessBinding
    private lateinit var adapter: BusinessListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageBusinessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add -> {
                    startActivity(Intent(this, AddWorkActivity::class.java))
                    true
                }
                else -> false
            }
        }

        binding.rvBusinessList.layoutManager = LinearLayoutManager(this)

        binding.layoutProgressBar.visibility =  View.VISIBLE
        businessList()


        binding.swipeRefresh.setOnRefreshListener {
            binding.layoutProgressBar.visibility =  View.VISIBLE
            businessList()
            binding.swipeRefresh.isRefreshing = false
        }

    }

    fun businessList() {

        val token: String? = "Bearer " + SaveSharedPreference.getInstance(this@ManageBusinessActivity).getToken()

        RetrofitInstance.apiInterface.businessList(token!!)
            .enqueue(object : Callback<List<BusinessListResponseData>?> {
                override fun onResponse(
                    call: Call<List<BusinessListResponseData>?>,
                    response: Response<List<BusinessListResponseData>?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility =  View.GONE
                        val businessResponse = response.body()!!
                        adapter = BusinessListAdapter(this@ManageBusinessActivity,businessResponse)
                        binding.rvBusinessList.adapter = adapter

                    } else {
                        binding.layoutProgressBar.visibility =  View.GONE
                        Toast.makeText(
                            this@ManageBusinessActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<BusinessListResponseData>?>, t: Throwable) {
                    binding.layoutProgressBar.visibility =  View.GONE
                    Toast.makeText(
                        this@ManageBusinessActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}