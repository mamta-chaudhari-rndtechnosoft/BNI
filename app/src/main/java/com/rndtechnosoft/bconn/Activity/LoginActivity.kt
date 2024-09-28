package com.rndtechnosoft.bconn.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.LoginBody
import com.rndtechnosoft.bconn.Model.LoginResponseData
import com.rndtechnosoft.bconn.databinding.ActivityLoginBinding
import com.google.firebase.messaging.FirebaseMessaging
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var deviceToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }
        val userId: String? = SaveSharedPreference.getInstance(this@LoginActivity).getUserId()
        Toast.makeText(this@LoginActivity,"Id: $userId",Toast.LENGTH_SHORT).show()

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

        }

        generateFcmToken()

    }

    private fun userLogin() {

        val loginBody = LoginBody(
            binding.etEmailLogin.editText?.text.toString(),
            binding.etPassword.editText?.text.toString(),
            deviceToken!!
        )

        RetrofitInstance.apiInterface.userLogin(loginBody)
            .enqueue(object : Callback<LoginResponseData?> {
                override fun onResponse(
                    call: Call<LoginResponseData?>,
                    response: Response<LoginResponseData?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE
                        val loginResponse: LoginResponseData = response.body()!!
                        val loginMemberResponse = loginResponse.member
                        val token = loginResponse.token
                        val userId = loginMemberResponse._id
                        val referralNumber = loginMemberResponse.refral_code


                        //Toast.makeText(this@LoginActivity, "Success", Toast.LENGTH_SHORT).show()
                        SaveSharedPreference.getInstance(this@LoginActivity).saveUserId(userId)
                        SaveSharedPreference.getInstance(this@LoginActivity).saveToken(token)
                        SaveSharedPreference.getInstance(this@LoginActivity).saveReferralNumber(referralNumber)

                        Toast.makeText(this@LoginActivity, "Login Successfully", Toast.LENGTH_SHORT).show()

                        val intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()

                    } else {

                        if(response.code() == 400){
                            binding.layoutProgressBar.visibility = View.GONE
                            Toast.makeText(
                                this@LoginActivity,
                                "Your Verification is Pending",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else if(response.code() == 404){
                            binding.layoutProgressBar.visibility = View.GONE
                            Toast.makeText(
                                this@LoginActivity,
                                "You are not a registered member",
                                Toast.LENGTH_SHORT
                            ).show()
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

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(
                    this,
                    "Fetching FCM registration token failed: ${task.exception}",
                    Toast.LENGTH_SHORT
                ).show()
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            //Toast.makeText(this, "FCM Token: $token", Toast.LENGTH_SHORT).show()
            Log.d("FCM", "Token: $token")
            deviceToken = token
        }

    }

}