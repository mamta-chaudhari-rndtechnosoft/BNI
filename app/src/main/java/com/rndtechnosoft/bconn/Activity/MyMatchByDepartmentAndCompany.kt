package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rndtechnosoft.bconn.Adapter.MyMatchByCompanyAdapter
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.MyMatchByCompaniesResponseData
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityMyMatchByDepartmentAndCompanyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyMatchByDepartmentAndCompany : AppCompatActivity() {

    private lateinit var binding: ActivityMyMatchByDepartmentAndCompanyBinding
    //private lateinit var viewModel: MyMatchViewModel
    private lateinit var adapter: MyMatchByCompanyAdapter
    private var companyName: String? = null
    private var deptName: String? = null
    private var userId: String? = null
    private var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMatchByDepartmentAndCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.toolbarMyMatchByCompany.setNavigationOnClickListener {
           finish()
       }

        binding.rvMyMatchByCompany.layoutManager =
            LinearLayoutManager(this@MyMatchByDepartmentAndCompany)

        companyName = intent.getStringExtra("companyName")
        deptName = intent.getStringExtra("dept")
        userId = SaveSharedPreference.getInstance(this@MyMatchByDepartmentAndCompany).getUserId()
        token = "bearer " + SaveSharedPreference.getInstance(this@MyMatchByDepartmentAndCompany).getToken()



        binding.layoutProgressBar.visibility = View.VISIBLE
        myMatchByDepartment()

        binding.swipeRefresh.setOnRefreshListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            myMatchByDepartment()
            binding.swipeRefresh.isRefreshing = false
        }

    }

    private fun myMatchByDepartment() {

        RetrofitInstance.apiInterface.myMatchByCompany(token!!, userId!!, companyName!!, deptName!!).enqueue(object : Callback<MyMatchByCompaniesResponseData?> {
            override fun onResponse(
                call: Call<MyMatchByCompaniesResponseData?>,
                response: Response<MyMatchByCompaniesResponseData?>
            ) {
                if(response.isSuccessful){
                    binding.layoutProgressBar.visibility = View.GONE

                    val matchResponse = response.body()!!
                    val myMatchByCompanyList = matchResponse.data as MutableList

                    adapter = MyMatchByCompanyAdapter(this@MyMatchByDepartmentAndCompany, myMatchByCompanyList)
                    binding.rvMyMatchByCompany.adapter = adapter

                    if (adapter.itemCount == 0) {
                        binding.rvMyMatchByCompany.visibility = View.GONE
                        binding.tvNoData.visibility = View.VISIBLE
                    } else {
                        binding.rvMyMatchByCompany.visibility = View.VISIBLE
                        binding.tvNoData.visibility = View.GONE
                    }
                }
                else{
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@MyMatchByDepartmentAndCompany,
                        "Response Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MyMatchByCompaniesResponseData?>, t: Throwable) {
                binding.layoutProgressBar.visibility = View.GONE
                Toast.makeText(
                    this@MyMatchByDepartmentAndCompany,
                    "Error: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Api Response", "Error: ${t.localizedMessage}")
            }
        })

    }
}