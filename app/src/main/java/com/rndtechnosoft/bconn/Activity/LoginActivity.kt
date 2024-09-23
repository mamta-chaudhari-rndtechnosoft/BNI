package com.rndtechnosoft.bconn.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.LoginBody
import com.rndtechnosoft.bconn.Model.LoginResponseData
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.ViewModel.LoginViewModel
import com.rndtechnosoft.bconn.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {

            if (binding.etEmailLogin.editText?.text.toString().isNullOrEmpty()) {
                Toast.makeText(this@LoginActivity, "Email cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.etPassword.editText?.text.toString().isNullOrEmpty()) {
                Toast.makeText(this@LoginActivity, "Password cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                binding.layoutProgressBar.visibility = View.VISIBLE
                userLogin()
            }

            //generateFcmToken()

        }



    }

    private fun userLogin() {

        val loginBody = LoginBody(binding.etEmailLogin.editText?.text.toString(), binding.etPassword.editText?.text.toString())

        RetrofitInstance.apiInterface.userLogin(loginBody).enqueue(object : Callback<LoginResponseData?> {
            override fun onResponse(
                call: Call<LoginResponseData?>,
                response: Response<LoginResponseData?>
            ) {
                if(response.isSuccessful){
                    binding.layoutProgressBar.visibility = View.GONE
                }
                else{
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@LoginActivity,
                        "Response Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponseData?>, t: Throwable) {
                binding.layoutProgressBar.visibility = View.GONE
                Toast.makeText(
                    this@LoginActivity,
                    "Error: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Api Response", "Error: ${t.localizedMessage}")
            }
        })

    }

    private fun generateFcmToken() {

        /*  FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(this, "Fetching FCM registration token failed: ${task.exception}", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Toast.makeText(this, "FCM Token: $token", Toast.LENGTH_SHORT).show()
            Log.d("FCM","Token: $token")
            // Here you can send the token to your server if needed
        }

    */
    }

}