package com.rndtechnosoft.bni.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rndtechnosoft.bni.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bni.Model.ContactLinks
import com.rndtechnosoft.bni.Model.ContactLinksProfileBody
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.databinding.ActivityEditProfileContactDetailBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class EditProfileContactDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileContactDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdate.setOnClickListener {
            editContactDetails()
        }

    }

    private fun editContactDetails() {

        val token: String? =
            "bearer " + SaveSharedPreference.getInstance(this@EditProfileContactDetailActivity)
                .getToken()

        val contactLinks = ContactLinks(
            whatsapp = binding.etWebsiteLink.text.toString(),
            facebook = binding.etFacebookLink.text.toString(),
            linkedin = binding.etLinkedInLink.text.toString(),
            twitter = binding.etEmail.text.toString()
        )

        val request = ContactLinksProfileBody(contactLinks)


        RetrofitInstance.apiInterface.updateContactLinks(token!!, request)
            .enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {

                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@EditProfileContactDetailActivity,
                            "Success: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()

                    } else {
                        Toast.makeText(
                            this@EditProfileContactDetailActivity,
                            "Response Not Success: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(
                        this@EditProfileContactDetailActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}