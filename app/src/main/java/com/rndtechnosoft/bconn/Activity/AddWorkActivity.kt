package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.R
import android.view.View
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.AddBusinessBody
import com.rndtechnosoft.bconn.Model.IndustryData
import com.rndtechnosoft.bconn.Model.IndustryResponseData
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityAddWorkBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddWorkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAddWork.setOnClickListener {
            //Validation Pending as not confirm yet

            binding.layoutProgressBar.visibility = View.VISIBLE
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
        val companyNumber:String = binding.etCompanyNumber.text.toString()
        val companyEmail:String = binding.etCompanyEmail.text.toString()
        val companyIndustry: String = binding.spinnerIndustry.selectedItem.toString()
        val personDesignation: String = binding.etDesignation.text.toString()
        val companyAddress: String = binding.etCompanyAddress.text.toString()
        val aboutCompany: String = binding.etAboutCompany.text.toString()
        //val whatsApp: String = binding.etWhatsAppLink.text.toString()

        val cName = RequestBody.create("text/plain".toMediaTypeOrNull(), companyName)
        val cNumber = RequestBody.create("text/plain".toMediaTypeOrNull(),companyNumber)
        val cEmail = RequestBody.create("text/plain".toMediaTypeOrNull(),companyEmail)
        val cIndustry = RequestBody.create("text/plain".toMediaTypeOrNull(), companyIndustry)
        val designation = RequestBody.create("text/plain".toMediaTypeOrNull(), personDesignation)
        val address = RequestBody.create("text/plain".toMediaTypeOrNull(), companyAddress)
        val about = RequestBody.create("text/plain".toMediaTypeOrNull(), aboutCompany)
        //val whatsAppLink = RequestBody.create("text/plain".toMediaTypeOrNull(), whatsApp)

        /*val addBusinessBody = AddBusinessBody(
            companyName,
            companyIndustry,
            personDesignation,
            companyAddress,
            aboutCompany
        )*/

        RetrofitInstance.apiInterface.createBusiness(
            token!!,
            userId,
            cName,
            cEmail,
            cNumber,
            cIndustry,
            designation,
            address,
            about
        ).enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@AddWorkActivity,
                            "Business Created Successfully.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()

                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Log.d("Api Response", "Response Error: ${response.code()}")
                        Toast.makeText(
                            this@AddWorkActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
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