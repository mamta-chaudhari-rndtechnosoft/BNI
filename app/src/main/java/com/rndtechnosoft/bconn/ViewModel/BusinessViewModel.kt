package com.rndtechnosoft.bconn.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rndtechnosoft.bconn.Model.IndustryData
import com.rndtechnosoft.bconn.Repository.BusinessRepository
import okhttp3.RequestBody
import okhttp3.ResponseBody

class BusinessViewModel() : ViewModel() {

    private val repository = BusinessRepository()
    private val businessResult = MutableLiveData<Result<ResponseBody>>()
    private val industryData = MutableLiveData<List<IndustryData>>()

    fun addBusiness(
        token: String,
        companyName: RequestBody,
        industry: RequestBody,
        designation: RequestBody,
        companyAddress: RequestBody,
        aboutCompany: RequestBody,
        whatsAppContactLink: RequestBody
    ) {
        repository.addBusiness(token,companyName,industry,designation,companyAddress,aboutCompany,whatsAppContactLink).observeForever {
            businessResult.value = it
        }
    }

    fun setBusinessResult():MutableLiveData<Result<ResponseBody>>{
        return businessResult
    }

    fun getIndustry(){
        repository.getIndustry().observeForever {
            industryData.value = it
        }
    }

    fun setIndustry():MutableLiveData<List<IndustryData>>{
        return industryData
    }
}