package com.rndtechnosoft.bconn.Repository

import androidx.lifecycle.MutableLiveData
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.Data
import com.rndtechnosoft.bconn.Model.MatchedCompany
import com.rndtechnosoft.bconn.Model.MyAllMatchesResponseData
import com.rndtechnosoft.bconn.Model.MyMatchByCompaniesResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyMatchRepository {

    private val errorLiveData = MutableLiveData<String>()

    fun myAllMatches(token: String, userId: String): MutableLiveData<MutableList<MatchedCompany>> {

        val matchedCompanyLiveData = MutableLiveData<MutableList<MatchedCompany>>()

        RetrofitInstance.apiInterface.myAllMatches(token, userId)
            .enqueue(object : Callback<MyAllMatchesResponseData?> {
                override fun onResponse(
                    call: Call<MyAllMatchesResponseData?>,
                    response: Response<MyAllMatchesResponseData?>
                ) {
                    if (response.isSuccessful) {
                        matchedCompanyLiveData.value =
                            response.body()!!.matchedCompanies as MutableList
                    } else {
                        errorLiveData.value = "Response Not Successful"
                    }
                }

                override fun onFailure(call: Call<MyAllMatchesResponseData?>, t: Throwable) {
                    errorLiveData.value = "Error: ${t.localizedMessage}"
                }
            })

        return matchedCompanyLiveData
    }

    fun myMatchByCompany(
        token: String,
        userId: String,
        company: String,
        department: String
    ): MutableLiveData<MutableList<Data>> {

        val myMatchedByCompany = MutableLiveData<MutableList<Data>>()

        RetrofitInstance.apiInterface.myMatchByCompany(token, userId, company, department)
            .enqueue(object : Callback<MyMatchByCompaniesResponseData?> {
                override fun onResponse(
                    call: Call<MyMatchByCompaniesResponseData?>,
                    response: Response<MyMatchByCompaniesResponseData?>
                ) {
                    if (response.isSuccessful) {

                        myMatchedByCompany.value = response.body()!!.data as MutableList

                    } else {
                        errorLiveData.value = "Response Not Success"
                    }

                }

                override fun onFailure(call: Call<MyMatchByCompaniesResponseData?>, t: Throwable) {
                    errorLiveData.value = "Error: ${t.localizedMessage}"
                }
            })

        return myMatchedByCompany
    }

    fun errorData():MutableLiveData<String>{
        return errorLiveData
    }
}