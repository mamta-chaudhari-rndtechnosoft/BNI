package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.BusinessInfoResponseData
import com.rndtechnosoft.bconn.Model.IndustryData
import com.rndtechnosoft.bconn.Model.IndustryResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityEditCompanyDetailsBinding
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCompanyDetailsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEditCompanyDetailsBinding
    private var selectedIndustry: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCompanyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnUpdate.setOnClickListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            editCompanyDetails()
        }

        getBusinessInfo()

    }

    private fun editCompanyDetails(){
        val token:String? = SaveSharedPreference.getInstance(this@EditCompanyDetailsActivity).getToken()
        val businessId: String? = intent.getStringExtra("businessId")

        val companyName = binding.etCompanyName.text.toString()
        val industryCategory = binding.spinnerIndustry.selectedItem.toString()
        val designation = binding.etDesignation.text.toString()
        val aboutCompany = binding.etAboutCompany.text.toString()
        val address = binding.etCompanyAddress.text.toString()

        val bCompanyName = if(companyName.isNotEmpty()) RequestBody.create("text/plain".toMediaTypeOrNull(),companyName) else null
        val bIndustryCategory = if (industryCategory.isNotEmpty()) RequestBody.create("text/plain".toMediaTypeOrNull(), industryCategory) else null
        val bDesignation = if (designation.isNotEmpty()) RequestBody.create("text/plain".toMediaTypeOrNull(), designation) else null
        val bAboutCompany = if (aboutCompany.isNotEmpty()) RequestBody.create("text/plain".toMediaTypeOrNull(), aboutCompany) else null
        val bAddress = if (address.isNotEmpty()) RequestBody.create("text/plain".toMediaTypeOrNull(), address) else null

        RetrofitInstance.apiInterface.updateBusinessDetails(
            token!!,businessId,bCompanyName,bIndustryCategory,bDesignation,bAboutCompany,bAddress)
            .enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    if(response.isSuccessful){
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@EditCompanyDetailsActivity,
                            "Details Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                    }
                    else{
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@EditCompanyDetailsActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@EditCompanyDetailsActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun getBusinessInfo() {

        val token: String? =
            "Bearer " + SaveSharedPreference.getInstance(this@EditCompanyDetailsActivity).getToken()

        val businessId: String? = intent.getStringExtra("businessId")

        RetrofitInstance.apiInterface.getBusinessInfo(token!!, businessId!!)
            .enqueue(object : Callback<BusinessInfoResponseData?> {
                override fun onResponse(
                    call: Call<BusinessInfoResponseData?>,
                    response: Response<BusinessInfoResponseData?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE
                        val businessInfoResponseData = response.body()!!
                        val businessData = businessInfoResponseData.data
                        val message = businessInfoResponseData.message

                        val companyName = businessData.companyName
                        val industryName = businessData.industryName
                        val designationName = businessData.designation
                        val aboutCompany = businessData.aboutCompany
                        val address = businessData.companyAddress


                        binding.etAboutCompany.setText(aboutCompany)
                        binding.etDesignation.setText(designationName)
                        binding.etCompanyAddress.setText(address)
                        binding.etCompanyName.setText(companyName)

                        // Store the selected industry name
                        selectedIndustry = businessData.industryName

                        // Fetch and set the industries in the spinner
                        getAllIndustry(selectedIndustry)


                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@EditCompanyDetailsActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<BusinessInfoResponseData?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@EditCompanyDetailsActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }

    private fun getAllIndustry(selectedIndustry: String?) {
        val token: String? = "Bearer " + SaveSharedPreference.getInstance(this@EditCompanyDetailsActivity).getToken()

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
                        industryTitleList.add("Select Industry") // Add default option

                        industryTitleList.addAll(industryList.map { it.name })

                        val adapter = ArrayAdapter(
                            this@EditCompanyDetailsActivity,
                            android.R.layout.simple_spinner_dropdown_item,
                            industryTitleList
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerIndustry.adapter = adapter

                        // Set the selected value in the spinner
                        if (selectedIndustry != null) {
                            val selectedPosition = industryTitleList.indexOf(selectedIndustry)
                            if (selectedPosition >= 0) {
                                binding.spinnerIndustry.setSelection(selectedPosition)
                            }
                        }

                    } else {
                        Toast.makeText(
                            this@EditCompanyDetailsActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<IndustryResponseData?>, t: Throwable) {
                    Toast.makeText(
                        this@EditCompanyDetailsActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

}