package com.rndtechnosoft.bconn.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.AddMyAskBody
import com.rndtechnosoft.bconn.Model.AddMyAskResponseData
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityAddMyAskBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMyAskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMyAskBinding
    //private lateinit var myAskViewModel: MyAskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMyAskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAddMyAsk.setNavigationOnClickListener {
            finish()
        }

        //myAskViewModel = ViewModelProvider(this@AddMyAskActivity)[MyAskViewModel::class.java]

        /*myAskViewModel.observeAddMyAskResult().observe(this@AddMyAskActivity, Observer {
            when {
                it.isSuccess -> {
                    val addMyAskResponseBody = it.getOrNull()!!
                    val status: String = addMyAskResponseBody.status
                    val message: String = addMyAskResponseBody.message

                    when (status) {
                        "success" -> {
                            Toast.makeText(this@AddMyAskActivity, message, Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }

                        "failed" -> {
                            Toast.makeText(this@AddMyAskActivity, message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }

                it.isFailure -> {
                    val error = it.exceptionOrNull()
                    Log.d("Api Response", "Failed: ${error?.message.toString()}")
                    Toast.makeText(
                        this@AddMyAskActivity,
                        "Failed: ${error?.message.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })*/

        binding.btnAddAsk.setOnClickListener {
            //validation pending
            binding.layoutProgressBar.visibility = View.VISIBLE
            addMyAsk()
        }

    }

    private fun addMyAsk() {
        val token: String? = "Bearer " + SaveSharedPreference.getInstance(this@AddMyAskActivity).getToken()
        val userId:String? = SaveSharedPreference.getInstance(this@AddMyAskActivity).getUserId()
        val companyName = binding.etCompany.editText?.text.toString()
        val deptName = binding.etDepartment.editText?.text.toString()
        val companyMessage = binding.etMessage.editText?.text.toString()

        val addMyAskBody = AddMyAskBody(companyName, deptName, companyMessage)

        //myAskViewModel.addMyAskResult(addMyAskBody, token!!)
        RetrofitInstance.apiInterface.addMyAsk(token!!,userId!!,addMyAskBody).enqueue(object : Callback<AddMyAskResponseData?> {
            override fun onResponse(
                call: Call<AddMyAskResponseData?>,
                response: Response<AddMyAskResponseData?>
            ) {
               if(response.isSuccessful){
                   binding.layoutProgressBar.visibility = View.GONE
                   Toast.makeText(
                       this@AddMyAskActivity,
                       "Ask Added Successfully.",
                       Toast.LENGTH_SHORT
                   ).show()

                   finish()

               }
                else{
                   binding.layoutProgressBar.visibility = View.GONE
                   Toast.makeText(
                       this@AddMyAskActivity,
                       "Response Error: ${response.code()}",
                       Toast.LENGTH_SHORT
                   ).show()
                }
            }

            override fun onFailure(call: Call<AddMyAskResponseData?>, t: Throwable) {
                binding.layoutProgressBar.visibility = View.GONE
                Toast.makeText(
                    this@AddMyAskActivity,
                    "Error: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Api Response", "Error: ${t.localizedMessage}")
            }
        })

    }

}