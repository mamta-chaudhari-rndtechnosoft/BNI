package com.rndtechnosoft.bni.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rndtechnosoft.bni.Model.LoginBody
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.ViewModel.LoginViewModel
import com.rndtechnosoft.bni.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

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

                userLogin()
            }


        }

        loginViewModel = ViewModelProvider(this@LoginActivity)[LoginViewModel::class.java]

        loginViewModel.observeLoginResult().observe(this, Observer {
            when {
                it.isSuccess -> {
                    val loginResponseBody = it.getOrNull()!!
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val userId: String = loginResponseBody.userId
                    val token: String = loginResponseBody.token
                    val status: String = loginResponseBody.status
                    val message: String = loginResponseBody.message

                    when (status) {
                        "success" -> {

                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()

                            SaveSharedPreference.getInstance(this@LoginActivity).saveUserId(userId)
                            SaveSharedPreference.getInstance(this@LoginActivity).saveToken(token)
                            SaveSharedPreference.getInstance(this@LoginActivity).saveStatus(status)
                            val intent = Intent(this@LoginActivity,MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                            //startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                        }


                        "failed" -> {
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                it.isFailure -> {
                    val error = it.exceptionOrNull()
                    Log.d("Api Response","Login Failed: ${error?.message.toString()}")
                    Toast.makeText(
                        this,
                        "Login Failed: Please provide correct username and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }

    private fun userLogin() {

        val email = binding.etEmailLogin.editText?.text.toString()
        val password = binding.etPassword.editText?.text.toString()

        val loginBody = LoginBody(email, password)

        loginViewModel.loginUser(loginBody)

    }
}