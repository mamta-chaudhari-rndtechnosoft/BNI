package com.rndtechnosoft.bni.Activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.rndtechnosoft.bni.Model.RegisterUserBody
import com.rndtechnosoft.bni.ViewModel.RegistrationViewModel
import com.rndtechnosoft.bni.databinding.ActivityRegistrationBinding


class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModel: RegistrationViewModel
    private lateinit var countryNames: List<String>
    private lateinit var cityNames: List<String>
    private lateinit var chapterNames: List<String>
    private var countryName: String? = null
    private var cityName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLogIn.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
        }

        countryNames = emptyList()

        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)

        /*
        viewModel.countries.observe(this, Observer {
            val countryNames = it.map { it.name }
            Toast.makeText(this@RegistrationActivity, "Data: $countryNames", Toast.LENGTH_SHORT).show()
        })
        */

        viewModel.getCountries()

        viewModel.observeCountryLiveData().observe(this@RegistrationActivity, Observer {
            countryNames = it.map {
                countryName = it.name
                it.name
            }
            setUpSpinnerCountry()
            //viewModel.getCity(countryName!!)
            /*if (countryNames.isNotEmpty()) {
                countryName = countryNames.first() // Use the first country name or any logic to select a country
                viewModel.getCity(countryName!!)
                //Toast.makeText(this@RegistrationActivity,"Country: $countryName",Toast.LENGTH_SHORT).show()
            }*/
        })


        viewModel.observeCityLiveData().observe(this@RegistrationActivity, Observer {
            cityNames = it.map {
                cityName = it.name
                it.name
            }
            setUpSpinnerCity()
            //viewModel.getChapter(cityName!!)
           /* if (cityNames.isNotEmpty()) {
                cityName = cityNames.first() // Use the first city name or any logic to select a city
                viewModel.getChapter(cityName!!)
                Toast.makeText(this@RegistrationActivity,"City: $cityName",Toast.LENGTH_SHORT).show()
            }*/
        })


        viewModel.observeChapterLiveData().observe(this@RegistrationActivity, Observer {
            chapterNames = it.map {
                it.name
            }
            setUpSpinnerChapter()
        })

        binding.spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                if (position > 0) {
                    countryName = countryNames[position - 1]
                    viewModel.getCity(countryName!!)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        binding.spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                if (position > 0) {
                    cityName = cityNames[position - 1]
                    viewModel.getChapter(cityName!!)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


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
            } else if (binding.spinnerChapter.selectedItemPosition == 0) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select the Chapter",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                userRegister()
            }
        }

        viewModel.observeRegisterResult().observe(this, Observer {
            when {
                it.isSuccess -> {
                    val responseBody = it.getOrNull()!!
                    val status: String = responseBody.status
                    val message: String = responseBody.message

                    when (status) {

                        "true" -> {
                            Toast.makeText(
                                this@RegistrationActivity,
                                "$message",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent =
                                Intent(this@RegistrationActivity, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }

                        "failed" -> {
                            Toast.makeText(
                                this@RegistrationActivity,
                                "$message",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    // Navigate to another activity or update UI
                }

                it.isFailure -> {
                    val error = it.exceptionOrNull()
                    Toast.makeText(
                        this,
                        "Registration Failed: ${error?.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        })
    }


    private fun userRegister() {
        val email = binding.etEmail.editText?.text.toString()
        val name = binding.etName.editText?.text.toString()
        val mobile = binding.etMobile.editText?.text.toString()
        val password = binding.etPassword.editText?.text.toString()
        val confirmPassword = binding.etConfirmPassword.editText?.text.toString()
        val country = binding.spinnerCountry.selectedItem.toString()
        val city = binding.spinnerCity.selectedItem.toString()
        val chapter = binding.spinnerChapter.selectedItem.toString()

        val registerUserBody =
            RegisterUserBody(email, name, mobile, password, confirmPassword, country, city, chapter)
        viewModel.registerUser(registerUserBody)
    }


    private fun setUpSpinnerCountry() {

        val countryTitles = mutableListOf("Select Country")

        //rejectionReasonString.addAll(rejectListResponseList.map { it.Rejection_Reason })

        countryTitles.addAll(countryNames)

        // if (binding.spinnerCountry != null) {}
        val countryAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, countryTitles)

        countryAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.spinnerCountry.adapter = countryAdapter

    }

    private fun setUpSpinnerCity() {

        val cityTitle = mutableListOf("Select City")

        //rejectionReasonString.addAll(rejectListResponseList.map { it.Rejection_Reason })

        cityTitle.addAll(cityNames)

        // if (binding.spinnerCountry != null) {}
        val cityAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, cityTitle)

        cityAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.spinnerCity.adapter = cityAdapter

    }

    private fun setUpSpinnerChapter() {

        val chapterTitles = mutableListOf("Select Chapter")

        //rejectionReasonString.addAll(rejectListResponseList.map { it.Rejection_Reason })

        chapterTitles.addAll(chapterNames)

        // if (binding.spinnerCountry != null) {}
        val chapterAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, chapterTitles)

        chapterAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.spinnerChapter.adapter = chapterAdapter

    }

    /*
    private fun fetchCountry() {
        Toast.makeText(this@RegistrationActivity,"Inside function",Toast.LENGTH_SHORT).show()
        RetrofitInstance.apiInterface.getCountry().enqueue(object : Callback<CountryResponse?> {
            override fun onResponse(
                call: Call<CountryResponse?>,
                response: Response<CountryResponse?>
            ) {
                if (response.isSuccessful) {
                    val countryResponse = response.body()!!
                    val list:List<CountryData> = countryResponse.countryData

                    val name = list.map {
                        it.name
                    }

                    Toast.makeText(this@RegistrationActivity, "CountryName: $name", Toast.LENGTH_SHORT)
                        .show()


                } else {
                    Toast.makeText(this@RegistrationActivity, "Response not success", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<CountryResponse?>, t: Throwable) {
                Toast.makeText(this@RegistrationActivity, "Response Error: ${t.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
                Log.d("Api Response","Error: ${t.localizedMessage}")
            }
        })
    }
    */

}
