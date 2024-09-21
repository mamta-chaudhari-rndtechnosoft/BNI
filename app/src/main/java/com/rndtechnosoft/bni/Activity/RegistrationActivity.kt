package com.rndtechnosoft.bni.Activity

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rndtechnosoft.bni.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bni.Model.CityData
import com.rndtechnosoft.bni.Model.CityResponse
import com.rndtechnosoft.bni.Model.CountryData
import com.rndtechnosoft.bni.Model.CountryResponse

import com.rndtechnosoft.bni.Model.RegisterUserBody
import com.rndtechnosoft.bni.ViewModel.RegistrationViewModel
import com.rndtechnosoft.bni.databinding.ActivityRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var countryNames: List<String>
    private lateinit var cityNames: List<CityData>
    private lateinit var chapterNames: List<String>
    private var countryName: String? = null
    private var cityName: String? = null

    companion object {
        private const val REQUEST_CODE_ADD_KEYWORDS = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLogIn.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
        }

        /*binding.tvAddKeyWords.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, KeyWordsActivity::class.java))
        }*/


        countryNames = emptyList()

        binding.layoutProgressBar.visibility = View.VISIBLE
        fetchCountry()


        // btn register
        binding.btnRegister.setOnClickListener {

            if (binding.etName.editText?.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Enter Name ",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.etEmail.editText?.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Enter Email ",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.etMobile.editText?.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Enter Mobile No ",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.spinnerCountry.selectedItemPosition == 0) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select the Country",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.spinnerCity.selectedItemPosition == 0) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select the Ciy",
                    Toast.LENGTH_SHORT
                ).show()
            }/* else if (binding.spinnerChapter.selectedItemPosition == 0) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select the Chapter",
                    Toast.LENGTH_SHORT
                ).show()
            }*/ else {
                userRegister()
            }
        }

    }

    private fun userRegister() {
        val email = binding.etEmail.editText?.text.toString()
        val name = binding.etName.editText?.text.toString()
        val mobile = binding.etMobile.editText?.text.toString()
        val password = binding.etPassword.editText?.text.toString()
        val confirmPassword = binding.etConfirmPassword.editText?.text.toString()
        val country = binding.spinnerCountry.selectedItem.toString()
        val city = binding.spinnerCity.selectedItem.toString()
        //val chapter = binding.spinnerChapter.selectedItem.toString()

        val registerUserBody = RegisterUserBody(email, name, mobile, password, confirmPassword, country, city)
    }


    private fun fetchCountry() {
        RetrofitInstance.apiInterface.getCountry().enqueue(object : Callback<CountryResponse?> {
            override fun onResponse(
                call: Call<CountryResponse?>,
                response: Response<CountryResponse?>
            ) {
                if (response.isSuccessful) {

                    binding.layoutProgressBar.visibility = View.GONE

                    val countryResponse = response.body()!!
                    val countryList: List<CountryData> = countryResponse.countryData

                    val countryNames = mutableListOf("Select Country")

                    countryNames.addAll(countryList.map { it.name })

                    val adapter = ArrayAdapter(
                        this@RegistrationActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        countryNames
                    )

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                    binding.spinnerCountry.adapter = adapter



                } else {
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Response Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<CountryResponse?>, t: Throwable) {
                binding.layoutProgressBar.visibility = View.GONE
                Toast.makeText(
                    this@RegistrationActivity,
                    "Error: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Api Response", "Error: ${t.localizedMessage}")
            }
        })
    }

    private fun fetchCity() {

        RetrofitInstance.apiInterface.getCity(countryName!!)
            .enqueue(object : Callback<CityResponse?> {
                override fun onResponse(
                    call: Call<CityResponse?>,
                    response: Response<CityResponse?>
                ) {
                    if (response.isSuccessful) {
                        val cityResponse: CityResponse = response.body()!!
                        val cityList = cityResponse.cityData

                        val adapter = ArrayAdapter(this@RegistrationActivity,R.layout.simple_spinner_dropdown_item,cityList)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerCity.adapter = adapter


                    } else {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("Api Response", "Response Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<CityResponse?>, t: Throwable) {
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("Api Response", "Error: ${t.localizedMessage}")
                }
            })

    }
}
