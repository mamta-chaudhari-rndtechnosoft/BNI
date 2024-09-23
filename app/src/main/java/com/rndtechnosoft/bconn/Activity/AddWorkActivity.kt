package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.ViewModel.BusinessViewModel
import com.rndtechnosoft.bconn.databinding.ActivityAddWorkBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody



class AddWorkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkBinding
    private lateinit var viewModel: BusinessViewModel
    private lateinit var industryNames: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@AddWorkActivity)[BusinessViewModel::class.java]

        viewModel.getIndustry()
        viewModel.setIndustry().observe(this@AddWorkActivity, Observer {
            industryNames =  it.map {
                it.name
            }
            setUpIndustrySpinner()
        })

        binding.btnAddWork.setOnClickListener {
            //Validation Pending
            addBusiness()
        }

    }

    private fun addBusiness() {
        val token: String? = SaveSharedPreference.getInstance(this@AddWorkActivity).getToken()

        val companyName: String = binding.etCompanyName.text.toString()
        val companyIndustry: String = binding.spinnerIndustry.selectedItem.toString()
        val personDesignation: String = binding.etDesignation.text.toString()
        val companyAddress: String = binding.etCompanyAddress.text.toString()
        val aboutCompany: String = binding.etAboutCompany.text.toString()
        val whatsApp: String = binding.etWhatsAppLink.text.toString()

        val cName = RequestBody.create("text/plain".toMediaTypeOrNull(), companyName)
        val cIndustry = RequestBody.create("text/plain".toMediaTypeOrNull(), companyIndustry)
        val designation = RequestBody.create("text/plain".toMediaTypeOrNull(), personDesignation)
        val address = RequestBody.create("text/plain".toMediaTypeOrNull(), companyAddress)
        val about = RequestBody.create("text/plain".toMediaTypeOrNull(), aboutCompany)
        val whatsAppLink = RequestBody.create("text/plain".toMediaTypeOrNull(), whatsApp)

        viewModel.addBusiness(token!!, cName, cIndustry, designation, address, about, whatsAppLink)

        viewModel.setBusinessResult().observe(this@AddWorkActivity, Observer {
            when {
                it.isSuccess -> {
                    Toast.makeText(this@AddWorkActivity, "Success!!", Toast.LENGTH_SHORT).show()
                }

                it.isFailure -> {
                    val error = it.exceptionOrNull()
                    Log.d("Api Response", "Login Failed: ${error?.message.toString()}")
                    Toast.makeText(
                        this,
                        "Login Failed: Please provide correct username and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }

    private fun setUpIndustrySpinner(){
        val industryTitle = mutableListOf("Select Industry")

        industryTitle.addAll(industryNames)

        val industryAdapter = ArrayAdapter(this, R.layout.simple_spinner_item,industryTitle)

        industryAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.spinnerIndustry.adapter = industryAdapter
    }

    /*private fun getAllIndustry() {
        RetrofitInstance.apiInterface.getAllIndustry()
            .enqueue(object : Callback<IndustryResponseData?> {
                override fun onResponse(
                    call: Call<IndustryResponseData?>,
                    response: Response<IndustryResponseData?>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@AddWorkActivity,
                            "Success !!",
                            Toast.LENGTH_SHORT
                        ).show()

                        val industryResponse:IndustryResponseData = response.body()!!
                        industryNames = industryResponse.industryData


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
    }*/

}