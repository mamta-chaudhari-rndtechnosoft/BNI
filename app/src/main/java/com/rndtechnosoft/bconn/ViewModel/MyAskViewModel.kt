package com.rndtechnosoft.bconn.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rndtechnosoft.bconn.Model.AddMyAskBody
import com.rndtechnosoft.bconn.Model.AddMyAskResponseData
import com.rndtechnosoft.bconn.Model.MyAskListData
import com.rndtechnosoft.bconn.Repository.MyAskRepository


class MyAskViewModel() : ViewModel() {

    private val myAskRepository = MyAskRepository()
    private val addMyAskResultLiveData = MutableLiveData<Result<AddMyAskResponseData>>()
    private val myAskLisLiveData = MutableLiveData<MutableList<MyAskListData>>()
    private val errorLiveData = MutableLiveData<String>()

    fun addMyAskResult(addMyAskBody: AddMyAskBody,token:String) {
        myAskRepository.addMyAsk(addMyAskBody,token).observeForever {
            addMyAskResultLiveData.value = it
        }
    }

    fun observeAddMyAskResult(): MutableLiveData<Result<AddMyAskResponseData>> {
        return addMyAskResultLiveData
    }

    fun myAskListResult(userId: String,token: String) {
        myAskRepository.myAsk(userId,token).observeForever {
            myAskLisLiveData.value = it
        }

        myAskRepository.getErrorLiveData().observeForever {
            errorLiveData.value = it
        }

    }

    fun observeMyAskResult(): MutableLiveData<MutableList<MyAskListData>> {
        return myAskLisLiveData
    }

    fun errorLiveData(): MutableLiveData<String> {
        return errorLiveData
    }
}