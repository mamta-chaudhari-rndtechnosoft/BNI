package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.BusinessInfoResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityEditCompanyContactDetailsBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCompanyContactDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCompanyContactDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCompanyContactDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnUpdate.setOnClickListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            editContactDetails()
        }

        getBusinessInfo()
    }

    private fun editContactDetails() {

        val token: String? =
            SaveSharedPreference.getInstance(this@EditCompanyContactDetailsActivity).getToken()
        val businessId: String? = intent.getStringExtra("businessId")

        val number = binding.etPhoneNumber.text.toString()
        val email = binding.etEmail.text.toString()
        val facebook = binding.etFacebookLink.text.toString()
        val linkedin = binding.etLinkedInLink.text.toString()


        /*  val bNumber = RequestBody.create("text/plain".toMediaTypeOrNull(), number)
          val bEmail = RequestBody.create("text/plain".toMediaTypeOrNull(),email)
          val bFacebook = RequestBody.create("text/plain".toMediaTypeOrNull(), facebook)
          val bLinkedin = RequestBody.create("text/plain".toMediaTypeOrNull(), linkedin)*/
        //val address = RequestBody.create("text/plain".toMediaTypeOrNull(), companyAddress)

        val bNumber = if (number.isNotEmpty()) RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            number
        ) else null
        val bEmail = if (email.isNotEmpty()) RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            email
        ) else null
        val bFacebook = if (facebook.isNotEmpty()) RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            facebook
        ) else null
        val bLinkedin = if (linkedin.isNotEmpty()) RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            linkedin
        ) else null

        RetrofitInstance.apiInterface.updateBusinessContactLinks(
            token!!, businessId, bNumber, bEmail, bFacebook, bLinkedin
        )
            .enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@EditCompanyContactDetailsActivity,
                            "Contacts Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@EditCompanyContactDetailsActivity,
                            "Response Not Success: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@EditCompanyContactDetailsActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun getBusinessInfo() {

        val token: String? =
            "Bearer " + SaveSharedPreference.getInstance(this@EditCompanyContactDetailsActivity)
                .getToken()

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

                        val number = businessData.mobile
                        val email = businessData.email
                        val facebook = businessData.facebook
                        val linkedIn = businessData.linkedin

                        binding.etEmail.setText(email)
                        binding.etPhoneNumber.setText(number)
                        binding.etLinkedInLink.setText(linkedIn)
                        binding.etFacebookLink.setText(facebook)


                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@EditCompanyContactDetailsActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<BusinessInfoResponseData?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@EditCompanyContactDetailsActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }
}