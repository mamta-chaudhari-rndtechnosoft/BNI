package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.R
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.AddBusinessBody
import com.rndtechnosoft.bconn.Model.IndustryData
import com.rndtechnosoft.bconn.Model.IndustryResponseData
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityAddWorkBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddWorkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkBinding

    //private lateinit var viewModel: BusinessViewModel
    //private lateinit var industryNames: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAddWork.setOnClickListener {
            //Validation Pending
            addBusiness()
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        getAllIndustry()

    }

    private fun addBusiness() {
        val token: String? =
            "Bearer " + SaveSharedPreference.getInstance(this@AddWorkActivity).getToken()

        val userId: String = SaveSharedPreference.getInstance(this@AddWorkActivity).getUserId()!!

        val companyName: String = binding.etCompanyName.text.toString()
        val companyIndustry: String = binding.spinnerIndustry.selectedItem.toString()
        val personDesignation: String = binding.etDesignation.text.toString()
        val companyAddress: String = binding.etCompanyAddress.text.toString()
        val aboutCompany: String = binding.etAboutCompany.text.toString()
        val whatsApp: String = binding.etWhatsAppLink.text.toString()

        /*val cName = RequestBody.create("text/plain".toMediaTypeOrNull(), companyName)
        val cIndustry = RequestBody.create("text/plain".toMediaTypeOrNull(), companyIndustry)
        val designation = RequestBody.create("text/plain".toMediaTypeOrNull(), personDesignation)
        val address = RequestBody.create("text/plain".toMediaTypeOrNull(), companyAddress)
        val about = RequestBody.create("text/plain".toMediaTypeOrNull(), aboutCompany)
        val whatsAppLink = RequestBody.create("text/plain".toMediaTypeOrNull(), whatsApp)*/

        val addBusinessBody = AddBusinessBody(
            companyName,
            companyIndustry,
            personDesignation,
            companyAddress,
            aboutCompany,
            whatsApp
        )

        RetrofitInstance.apiInterface.addBusiness(token!!, userId, addBusinessBody)
            .enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {

                    Toast.makeText(
                        this@AddWorkActivity,
                        "Success: ${response.code()}",
                        Toast.LENGTH_SHORT).show()

                } else {
                    Log.d("Api Response", "Response Error: ${response.code()}")
                    Toast.makeText(
                        this@AddWorkActivity,
                        "Response Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.d("Api Response", "Error: ${t.localizedMessage}")
                Toast.makeText(
                    this@AddWorkActivity,
                    "Error: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }


    private fun getAllIndustry() {
        val token: String? =
            "Bearer " + SaveSharedPreference.getInstance(this@AddWorkActivity).getToken()

        RetrofitInstance.apiInterface.getAllIndustry(token!!)
            .enqueue(object : Callback<IndustryResponseData?> {
                override fun onResponse(
                    call: Call<IndustryResponseData?>,
                    response: Response<IndustryResponseData?>
                ) {
                    if (response.isSuccessful) {

                        val industryResponse: IndustryResponseData = response.body()!!
                        val industryList: List<IndustryData> = industryResponse.industryData

                        val industryTitleList = mutableListOf<String>()

                        industryTitleList.add("Select Industry")

                        industryTitleList.addAll(industryList.map {
                            it.name
                        })

                        val adapter = ArrayAdapter(
                            this@AddWorkActivity,
                            R.layout.simple_spinner_dropdown_item,
                            industryTitleList
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerIndustry.adapter = adapter


                    } else {
                        Toast.makeText(
                            this@AddWorkActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<IndustryResponseData?>, t: Throwable) {
                    Toast.makeText(
                        this@AddWorkActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }

}