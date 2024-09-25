package com.rndtechnosoft.bconn.Activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.CityResponse
import com.rndtechnosoft.bconn.Model.CountryData
import com.rndtechnosoft.bconn.Model.CountryResponse
import com.rndtechnosoft.bconn.Model.RegisterResponseData

import com.rndtechnosoft.bconn.Model.RegisterUserBody
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private var countryName: String? = null


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


        //countryNames = emptyList()

        binding.layoutProgressBar.visibility = View.VISIBLE
        fetchCountry()

        binding.spinnerCountry.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) { // Ignore "Select Country"
                        countryName = parent.getItemAtPosition(position).toString()

                        binding.tvCity.visibility = View.VISIBLE
                        binding.spinnerCity.visibility = View.VISIBLE
                        binding.layoutProgressBar.visibility = View.VISIBLE
                        fetchCity(countryName!!)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Optional: Handle case where nothing is selected if needed
                }
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
            } else if (binding.etReferralCode.editText?.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Enter Referral Code ",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.etPassword.editText?.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Enter Password",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.etConfirmPassword.editText?.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Enter Confirm Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            /* else if (binding.spinnerChapter.selectedItemPosition == 0) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select the Chapter",
                    Toast.LENGTH_SHORT
                ).show()
            }*/
            else {
                binding.layoutProgressBar.visibility = View.VISIBLE
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
        val referralCode = binding.etReferralCode.editText?.text.toString()
        //val chapter = binding.spinnerChapter.selectedItem.toString()

        val registerUserBody = RegisterUserBody(
            name,
            email,
            mobile,
            country,
            city,
            password,
            confirmPassword,
            referralCode
        )

        RetrofitInstance.apiInterface.userRegister(registerUserBody)
            .enqueue(object : Callback<RegisterResponseData?> {
                override fun onResponse(
                    call: Call<RegisterResponseData?>,
                    response: Response<RegisterResponseData?>
                ) {
                    if (response.isSuccessful) {

                        val registerResponse: RegisterResponseData = response.body()!!
                        val responseData = registerResponse.newMember
                        val userId = responseData._id
                        val approveAdmin = responseData.approvedByadmin
                        val approveMember = responseData.approvedBymember


                        SaveSharedPreference.getInstance(this@RegistrationActivity).saveAdminApproveStatus(approveAdmin)
                        SaveSharedPreference.getInstance(this@RegistrationActivity).saveMemberApproveStatus(approveMember)
                        SaveSharedPreference.getInstance(this@RegistrationActivity).saveUserId(userId)


                        Toast.makeText(this@RegistrationActivity, "Register Successfully.", Toast.LENGTH_SHORT).show()

                        var intent: Intent = Intent(this@RegistrationActivity, ThankYouActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()

                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("Api Response", "Response Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<RegisterResponseData?>, t: Throwable) {
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

                    //countryName = binding.spinnerCountry.selectedItem.toString()
                    //fetchCity()


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

    private fun fetchCity(countryNames: String) {

        if (countryName.isNullOrEmpty()) {
            // No country selected, show default "Select City"
            val cityNames = mutableListOf("Select City")

            val adapter = ArrayAdapter(
                this@RegistrationActivity,
                R.layout.simple_spinner_dropdown_item,
                cityNames
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCity.adapter = adapter
        } else {

            RetrofitInstance.apiInterface.getCity(countryNames)
                .enqueue(object : Callback<List<CityResponse>?> {
                    override fun onResponse(
                        call: Call<List<CityResponse>?>,
                        response: Response<List<CityResponse>?>
                    ) {
                        if (response.isSuccessful) {
                            binding.layoutProgressBar.visibility = View.GONE

                            val cityList: List<CityResponse> = response.body()!!

                            val cityNames = mutableListOf("Select City")

                            cityNames.addAll(cityList.map { it.name })

                            val adapter = ArrayAdapter(
                                this@RegistrationActivity,
                                R.layout.simple_spinner_dropdown_item,
                                cityNames
                            )
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            binding.spinnerCity.adapter = adapter


                        } else {
                            binding.layoutProgressBar.visibility = View.GONE
                            Toast.makeText(
                                this@RegistrationActivity,
                                "Response Error: ${response.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("Api Response", "Response Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<List<CityResponse>?>, t: Throwable) {
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
    }

}
