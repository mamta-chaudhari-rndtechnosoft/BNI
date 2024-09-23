package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rndtechnosoft.bconn.Adapter.MyMatchByCompanyAdapter
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.ViewModel.MyMatchViewModel
import com.rndtechnosoft.bconn.databinding.ActivityMyMatchByDepartmentAndCompanyBinding

class MyMatchByDepartmentAndCompany : AppCompatActivity() {

    private lateinit var binding: ActivityMyMatchByDepartmentAndCompanyBinding
    private lateinit var viewModel: MyMatchViewModel
    private lateinit var adapter: MyMatchByCompanyAdapter
    private var companyName: String? = null
    private var deptName: String? = null
    private var userId: String? = null
    private var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMatchByDepartmentAndCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this@MyMatchByDepartmentAndCompany)[MyMatchViewModel::class.java]

        binding.rvMyMatchByCompany.layoutManager =
            LinearLayoutManager(this@MyMatchByDepartmentAndCompany)

        companyName = intent.getStringExtra("companyName")
        deptName = intent.getStringExtra("dept")
        userId = SaveSharedPreference.getInstance(this@MyMatchByDepartmentAndCompany).getUserId()
        token = "bearer " + SaveSharedPreference.getInstance(this@MyMatchByDepartmentAndCompany).getToken()


        myMatchByDepartment()

    }

    private fun myMatchByDepartment() {

        viewModel.setMyAllMatchByCompany(token!!, userId!!, companyName!!, deptName!!)

        viewModel.getMyAllMatchByCompany().observe(this@MyMatchByDepartmentAndCompany, Observer {
            adapter = MyMatchByCompanyAdapter(this@MyMatchByDepartmentAndCompany, it)
            binding.rvMyMatchByCompany.adapter = adapter
        })

        viewModel.getErrorData().observe(this@MyMatchByDepartmentAndCompany, Observer {
            Toast.makeText(this@MyMatchByDepartmentAndCompany, "Error: $it", Toast.LENGTH_SHORT)
                .show()
            Log.d("Api Response", "Error: $it")
        })
    }
}