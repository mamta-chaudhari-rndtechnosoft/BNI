package com.rndtechnosoft.bni.Repository

import androidx.lifecycle.MutableLiveData
import com.rndtechnosoft.bni.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bni.Model.IndustryData
import com.rndtechnosoft.bni.Model.IndustryResponseData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusinessRepository {

    fun addBusiness(
        token: String,
        companyName: RequestBody,
        industry: RequestBody,
        designation: RequestBody,
        companyAddress: RequestBody,
        aboutCompany: RequestBody,
        whatsAppContactLink: RequestBody
    ): MutableLiveData<Result<ResponseBody>> {
        val addBusinessResponse = MutableLiveData<Result<ResponseBody>>()

        RetrofitInstance.apiInterface.createBusiness(
            token,
            designation,
            aboutCompany,
            companyAddress,
            whatsAppContactLink
        ).enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful) {
                    addBusinessResponse.value = Result.success(response.body()!!)
                } else {
                    addBusinessResponse.value =
                        Result.failure(Throwable(response.errorBody()?.string()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                addBusinessResponse.value = Result.failure(t)
            }
        })

        return addBusinessResponse
    }

    fun getIndustry(): MutableLiveData<List<IndustryData>> {

        val data = MutableLiveData<List<IndustryData>>()

        RetrofitInstance.apiInterface.getAllIndustry()
            .enqueue(object : Callback<IndustryResponseData?> {
                override fun onResponse(
                    call: Call<IndustryResponseData?>,
                    response: Response<IndustryResponseData?>
                ) {
                    if (response.isSuccessful) {
                        data.value = response.body()!!.industryData
                    } else {
                        data.value = emptyList()
                    }
                }

                override fun onFailure(call: Call<IndustryResponseData?>, t: Throwable) {
                    data.value = emptyList()
                }
            })
        return data
    }
}