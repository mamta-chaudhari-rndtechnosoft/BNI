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
import com.rndtechnosoft.bni.Model.AddMyGivesBody
import com.rndtechnosoft.bni.R
import com.rndtechnosoft.bni.Util.SaveSharedPreference
import com.rndtechnosoft.bni.ViewModel.MyGivesViewModel
import com.rndtechnosoft.bni.databinding.ActivityAddMyGivesBinding

class AddMyGivesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMyGivesBinding
    private lateinit var myGivesViewModel: MyGivesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMyGivesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAddMyGives.setNavigationOnClickListener {
            finish()
        }

        myGivesViewModel = ViewModelProvider(this@AddMyGivesActivity)[MyGivesViewModel::class.java]

        myGivesViewModel.observeAddMyGivesResult().observe(this@AddMyGivesActivity, Observer {

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


        })

        binding.btnAddGives.setOnClickListener {
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

        myGivesViewModel.addMyGivesResult(addMyGives, token!!)

    }

}