package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.AddMyGivesBody
import com.rndtechnosoft.bconn.Model.AddMyGivesResponseData
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.ViewModel.MyGivesViewModel
import com.rndtechnosoft.bconn.databinding.ActivityAddMyGivesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AddMyGivesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMyGivesBinding
    //private lateinit var myGivesViewModel: MyGivesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMyGivesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAddMyGives.setNavigationOnClickListener {
            finish()
        }

        //myGivesViewModel = ViewModelProvider(this@AddMyGivesActivity)[MyGivesViewModel::class.java]

       /* myGivesViewModel.observeAddMyGivesResult().observe(this@AddMyGivesActivity, Observer {

            when {
                it.isSuccess -> {

                    val addMyGivesResponseBody = it.getOrNull()!!
                    val status: String = addMyGivesResponseBody.status
                    val message: String = addMyGivesResponseBody.message

                    when (status) {
                        "success" -> {
                            Toast.makeText(this@AddMyGivesActivity, message, Toast.LENGTH_SHORT)
                                .show()
                            finish()
                            //dismiss()
                        }

                        "failed" -> {
                            Toast.makeText(this@AddMyGivesActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                it.isFailure -> {
                    val error = it.exceptionOrNull()
                    Log.d("Api Response", "Failed: ${error?.message.toString()}")
                    Toast.makeText(
                        this@AddMyGivesActivity,
                        "Failed: ${error?.message.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })*/

        binding.btnAddGives.setOnClickListener {
            binding.layoutProgressBar.visibility = View.VISIBLE
            addMyGives()
        }


    }

    private fun addMyGives() {

        val token: String? =
            "bearer " + SaveSharedPreference.getInstance(this@AddMyGivesActivity).getToken()

        val companyName = binding.etCompany.editText?.text.toString()
        val deptName = binding.etDepartment.editText?.text.toString()
        val companyEmail = binding.etCompanyEmail.editText?.text.toString()
        val companyPhoneNumber = binding.etCompanyPhone.editText?.text.toString()
        val companyUrl = binding.etWebUrl.editText?.text.toString()

        val addMyGives =
            AddMyGivesBody(companyName, deptName, companyEmail, companyPhoneNumber, companyUrl)

        //myGivesViewModel.addMyGivesResult(addMyGives, token!!)

        RetrofitInstance.apiInterface.addMyGives(token!!,addMyGives).enqueue(object : Callback<AddMyGivesResponseData?> {
            override fun onResponse(
                call: Call<AddMyGivesResponseData?>,
                response: Response<AddMyGivesResponseData?>
            ) {
                if(response.isSuccessful){
                    binding.layoutProgressBar.visibility = View.GONE


                }
                else{
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@AddMyGivesActivity,
                        "Response Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<AddMyGivesResponseData?>, t: Throwable) {
                binding.layoutProgressBar.visibility = View.GONE
                Toast.makeText(
                    this@AddMyGivesActivity,
                    "Error: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Api Response", "Error: ${t.localizedMessage}")
            }
        })

    }

}