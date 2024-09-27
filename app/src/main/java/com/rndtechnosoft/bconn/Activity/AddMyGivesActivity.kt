package com.rndtechnosoft.bconn.Activity

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.AddMyGivesBody
import com.rndtechnosoft.bconn.Model.AddMyGivesResponseData
import com.rndtechnosoft.bconn.Model.DepartmentData
import com.rndtechnosoft.bconn.Model.DepartmentListResponseData
import com.rndtechnosoft.bconn.Model.IndustryData
import com.rndtechnosoft.bconn.Model.IndustryResponseData
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityAddMyGivesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMyGivesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMyGivesBinding
    //private lateinit var myGivesViewModel: MyGivesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMyGivesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAddMyGives.setNavigationOnClickListener {
            finish()
        }


        binding.btnAddGives.setOnClickListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            addMyGives()
        }

        fetchDepartment()

    }

    private fun addMyGives() {

        val token: String? = "Bearer " + SaveSharedPreference.getInstance(this@AddMyGivesActivity).getToken()

        val userId:String? = SaveSharedPreference.getInstance(this@AddMyGivesActivity).getUserId()

        val companyName = binding.etCompany.editText?.text.toString()

        //val deptName = binding.etDepartment.editText?.text.toString()
        val deptName =binding.spinnerDepartment.selectedItem.toString()

        val companyEmail = binding.etCompanyEmail.editText?.text.toString()
        val companyPhoneNumber = binding.etCompanyPhone.editText?.text.toString()
        val companyUrl = binding.etWebUrl.editText?.text.toString()

        val addMyGives = AddMyGivesBody(companyName, deptName, companyEmail, companyPhoneNumber, companyUrl)

        //myGivesViewModel.addMyGivesResult(addMyGives, token!!)

        RetrofitInstance.apiInterface.addMyGives(token!!,userId!!,addMyGives).enqueue(object : Callback<AddMyGivesResponseData?> {
            override fun onResponse(
                call: Call<AddMyGivesResponseData?>,
                response: Response<AddMyGivesResponseData?>
            ) {
                if(response.isSuccessful){
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(this@AddMyGivesActivity, "Give Added Successfully.", Toast.LENGTH_SHORT).show()
                    finish()

                }
                else{
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@AddMyGivesActivity,
                        "Response Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<AddMyGivesResponseData?>, t: Throwable) {
                binding.layoutProgressBar.visibility = View.GONE
                Toast.makeText(
                    this@AddMyGivesActivity,
                    "Error: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Api Response", "Error: ${t.localizedMessage}")
            }
        })
    }

    private fun fetchDepartment(){
        val token: String? = "Bearer " + SaveSharedPreference.getInstance(this@AddMyGivesActivity).getToken()

        RetrofitInstance.apiInterface.getAllDepartment(token!!).enqueue(object : Callback<DepartmentListResponseData?> {
            override fun onResponse(
                call: Call<DepartmentListResponseData?>,
                response: Response<DepartmentListResponseData?>
            ) {
                if(response.isSuccessful){
                    val departmentResponse: DepartmentListResponseData = response.body()!!
                    val departmentList: List<DepartmentData> = departmentResponse.data

                    val departmentTitleList = mutableListOf<String>()

                    departmentTitleList.add("Select Department")

                    departmentTitleList.addAll(departmentList.map {
                        it.name
                    })

                    val adapter = ArrayAdapter(
                        this@AddMyGivesActivity,
                        R.layout.simple_spinner_dropdown_item,
                        departmentTitleList
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerDepartment.adapter = adapter
                }
                else{
                    Toast.makeText(
                        this@AddMyGivesActivity,
                        "Response Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<DepartmentListResponseData?>, t: Throwable) {
                Toast.makeText(
                    this@AddMyGivesActivity,
                    "Error: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


}