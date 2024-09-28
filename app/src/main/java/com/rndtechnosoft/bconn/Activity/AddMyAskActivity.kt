package com.rndtechnosoft.bconn.Activity

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.AddMyAskBody
import com.rndtechnosoft.bconn.Model.AddMyAskResponseData
import com.rndtechnosoft.bconn.Model.CompanyResponse
import com.rndtechnosoft.bconn.Model.DepartmentData
import com.rndtechnosoft.bconn.Model.DepartmentListResponseData
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityAddMyAskBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMyAskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMyAskBinding
    private lateinit var companyAdapter: ArrayAdapter<String>
    private val companyList: MutableList<String> = mutableListOf()
    private var selectedCompanyName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMyAskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAddMyAsk.setNavigationOnClickListener {
            finish()
        }

        companyAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, companyList)
        //binding.autoTextCompany.setAdapter(companyAdapter)

        /*binding.autoTextCompany.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length >= 3) {
                    fetchFilteredCompanies(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })*/

        // Handle item click for selecting company or adding new one
        /*binding.autoTextCompany.setOnItemClickListener { _, _, position, _ ->
            val selectedCompany = companyList[position]
            if (selectedCompany == "Add new company") {
                showAddNewCompanyDialog(binding.autoTextCompany.text.toString())
            }
            else{
                selectedCompanyName = selectedCompany
            }
        }*/



        binding.btnAddAsk.setOnClickListener {
            //validation pending
            binding.layoutProgressBar.visibility = View.VISIBLE
            addMyAsk()
        }


        fetchDepartment()

    }

    private fun addMyAsk() {
        val token: String? =
            "Bearer " + SaveSharedPreference.getInstance(this@AddMyAskActivity).getToken()
        val userId: String? = SaveSharedPreference.getInstance(this@AddMyAskActivity).getUserId()
        val companyName = binding.etCompany.editText?.text.toString()
        //val deptName = binding.etDepartment.editText?.text.toString()
        val deptName = binding.spinnerDepartment.selectedItem.toString()
        val companyMessage = binding.etMessage.editText?.text.toString()

        val addMyAskBody = AddMyAskBody(companyName, deptName, companyMessage)
        //val addMyAskBody = AddMyAskBody(selectedCompanyName, deptName, companyMessage)

        //myAskViewModel.addMyAskResult(addMyAskBody, token!!)
        RetrofitInstance.apiInterface.addMyAsk(token!!, userId!!, addMyAskBody)
            .enqueue(object : Callback<AddMyAskResponseData?> {
                override fun onResponse(
                    call: Call<AddMyAskResponseData?>,
                    response: Response<AddMyAskResponseData?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@AddMyAskActivity,
                            "Ask Added Successfully.",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()

                    } else {
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

    private fun fetchDepartment() {
        val token: String? =
            "Bearer " + SaveSharedPreference.getInstance(this@AddMyAskActivity).getToken()

        RetrofitInstance.apiInterface.getAllDepartment(token!!)
            .enqueue(object : Callback<DepartmentListResponseData?> {
                override fun onResponse(
                    call: Call<DepartmentListResponseData?>,
                    response: Response<DepartmentListResponseData?>
                ) {
                    if (response.isSuccessful) {
                        val departmentResponse: DepartmentListResponseData = response.body()!!
                        val departmentList: List<DepartmentData> = departmentResponse.data

                        val departmentTitleList = mutableListOf<String>()

                        departmentTitleList.add("Select Department")

                        departmentTitleList.addAll(departmentList.map {
                            it.name
                        })

                        val adapter = ArrayAdapter(
                            this@AddMyAskActivity,
                            R.layout.simple_spinner_dropdown_item,
                            departmentTitleList
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerDepartment.adapter = adapter
                    } else {
                        Toast.makeText(
                            this@AddMyAskActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DepartmentListResponseData?>, t: Throwable) {
                    Toast.makeText(
                        this@AddMyAskActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }


    private fun fetchFilteredCompanies(query: String) {

        val token = "Bearer " + SaveSharedPreference.getInstance(this@AddMyAskActivity).getToken()
        val call = RetrofitInstance.apiInterface.getFilteredGives(token, query)

        call.enqueue(object : Callback<CompanyResponse?> {
            override fun onResponse(
                call: Call<CompanyResponse?>,
                response: Response<CompanyResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val companies = response.body()?.companies ?: emptyList()


                    companyList.clear()
                    companyList.addAll(companies.map { it.companyName })

                    if (companyList.isEmpty()) {
                        companyList.add("Add new company")
                    }
                    companyAdapter.notifyDataSetChanged()

                } else {
                    Toast.makeText(
                        this@AddMyAskActivity,
                        "Response Error: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<CompanyResponse?>, t: Throwable) {
                Toast.makeText(
                    this@AddMyAskActivity,
                    "Error: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun showAddNewCompanyDialog(companyName: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Company")
        builder.setMessage("No companies found. Do you want to add '$companyName' as a new company?")

        builder.setPositiveButton("Yes") { _, _ ->
            Toast.makeText(this, "New company added: $companyName", Toast.LENGTH_SHORT).show()
            selectedCompanyName = companyName
            binding.autoTextCompany.setText(companyName)
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}