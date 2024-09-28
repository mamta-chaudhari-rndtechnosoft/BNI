package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.ContactLinks
import com.rndtechnosoft.bconn.Model.ContactLinksProfileBody
import com.rndtechnosoft.bconn.Model.USerInfoResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityEditProfileContactDetailBinding
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileContactDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileContactDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnUpdate.setOnClickListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            editContactDetails()
        }

        getUserInfo()

    }

    private fun editContactDetails() {

        val token: String? = "Bearer " + SaveSharedPreference.getInstance(this@EditProfileContactDetailActivity).getToken()
        val userId:String = SaveSharedPreference.getInstance(this@EditProfileContactDetailActivity).getUserId()!!

        val name = binding.etName.text.toString()
        val number = binding.etPhoneNumber.text.toString()
        val email = binding.etEmail.text.toString()
        val facebook = binding.etFacebookLink.text.toString()
        val linkedin = binding.etLinkedInLink.text.toString()


        /*
        val bNumber = RequestBody.create("text/plain".toMediaTypeOrNull(), number)
        val bEmail = RequestBody.create("text/plain".toMediaTypeOrNull(),email)
        val bFacebook = RequestBody.create("text/plain".toMediaTypeOrNull(), facebook)
        val bLinkedin = RequestBody.create("text/plain".toMediaTypeOrNull(), linkedin)
        */

        val bName = if (name.isNotEmpty()) RequestBody.create("text/plain".toMediaTypeOrNull(), name) else null
        val bNumber = if (number.isNotEmpty()) RequestBody.create("text/plain".toMediaTypeOrNull(), number) else null
        val bEmail = if (email.isNotEmpty()) RequestBody.create("text/plain".toMediaTypeOrNull(), email) else null
        val bFacebook = if (facebook.isNotEmpty()) RequestBody.create("text/plain".toMediaTypeOrNull(), facebook) else null
        val bLinkedin = if (linkedin.isNotEmpty()) RequestBody.create("text/plain".toMediaTypeOrNull(), linkedin) else null

        RetrofitInstance.apiInterface.updateUserContactLinks(
            token!!,userId,bName,bNumber,bEmail,bFacebook,bLinkedin)
            .enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    if(response.isSuccessful){
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@EditProfileContactDetailActivity,
                            "Contacts Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                    }
                    else{
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@EditProfileContactDetailActivity,
                            "Response Not Success: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@EditProfileContactDetailActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }

    private fun getUserInfo() {

        val token: String? = "Bearer " + SaveSharedPreference.getInstance(this).getToken()
        val userId: String? = SaveSharedPreference.getInstance(this).getUserId()

        RetrofitInstance.apiInterface.getUserInfo(token!!, userId!!)
            .enqueue(object : Callback<USerInfoResponseData?> {
                override fun onResponse(
                    call: Call<USerInfoResponseData?>,
                    response: Response<USerInfoResponseData?>
                ) {
                    if (response.isSuccessful) {

                        val userInfoResponseData = response.body()!!
                        val userData = userInfoResponseData.data

                        val name: String = userData.name
                        val number: String = userData.mobile
                        val email: String = userData.email
                        val facebook:String = userData.facebook
                        val linkedIn:String = userData.linkedin


                        binding.etName.setText(name)
                        binding.etPhoneNumber.setText(number)
                        binding.etEmail.setText(email)
                        binding.etFacebookLink.setText(facebook)
                        binding.etLinkedInLink.setText(linkedIn)

                    } else {
                        Toast.makeText(
                            this@EditProfileContactDetailActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<USerInfoResponseData?>, t: Throwable) {
                    Toast.makeText(
                        this@EditProfileContactDetailActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }

}