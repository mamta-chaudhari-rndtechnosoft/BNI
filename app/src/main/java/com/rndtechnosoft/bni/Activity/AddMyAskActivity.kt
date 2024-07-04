package com.rndtechnosoft.bni.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rndtechnosoft.bni.Model.AddMyAskBody
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.ViewModel.MyAskViewModel
import com.rndtechnosoft.bni.databinding.ActivityAddMyAskBinding

class AddMyAskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMyAskBinding
    private lateinit var myAskViewModel: MyAskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMyAskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAddMyAsk.setNavigationOnClickListener {
            finish()
        }

        myAskViewModel = ViewModelProvider(this@AddMyAskActivity)[MyAskViewModel::class.java]

        myAskViewModel.observeAddMyAskResult().observe(this@AddMyAskActivity, Observer {
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
        })

        binding.btnAddAsk.setOnClickListener {
            addMyAsk()
        }

    }

    private fun addMyAsk() {

        val token: String? = "bearer " + SaveSharedPreference.getInstance(this@AddMyAskActivity).getToken()

        val companyName = binding.etCompany.editText?.text.toString()
        val deptName = binding.etDepartment.editText?.text.toString()
        val companyMessage = binding.etMessage.editText?.text.toString()

        val addMyAskBody = AddMyAskBody(companyName, deptName, companyMessage)

        myAskViewModel.addMyAskResult(addMyAskBody, token!!)

    }

}