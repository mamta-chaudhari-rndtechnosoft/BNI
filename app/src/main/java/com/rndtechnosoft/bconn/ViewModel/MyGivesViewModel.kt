package com.rndtechnosoft.bconn.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rndtechnosoft.bconn.Model.AddMyGivesBody
import com.rndtechnosoft.bconn.Model.AddMyGivesResponseData
import com.rndtechnosoft.bconn.Model.MyGivesData
import com.rndtechnosoft.bconn.Repository.MyGivesRepository

class MyGivesViewModel():ViewModel() {

    private val myGivesRepository = MyGivesRepository()
    private val addMyGivesResultLiveData = MutableLiveData<Result<AddMyGivesResponseData>>()
    private val getMyGivesLiveData = MutableLiveData<MutableList<MyGivesData>>()
    private val errorLiveData = MutableLiveData<String>()

   /* init {
        getMyGivesLiveData(token,userId)
    }
    */

    fun addMyGivesResult(addMyGivesBody: AddMyGivesBody,token:String){
        myGivesRepository.addMyGives(addMyGivesBody,token).observeForever {
            addMyGivesResultLiveData.value = it
        }
    }

    fun observeAddMyGivesResult():MutableLiveData<Result<AddMyGivesResponseData>>{
        return addMyGivesResultLiveData
    }

    fun getMyGivesLiveData(token: String,userId:String){
        myGivesRepository.getMyGives(token,userId).observeForever {
            getMyGivesLiveData.value = it
        }

        myGivesRepository.getErrorLiveData().observeForever {
            errorLiveData.value = it
        }
    }

    fun observeGetMyGives():MutableLiveData<MutableList<MyGivesData>>{
        return getMyGivesLiveData
    }

    fun errorLiveData(): MutableLiveData<String> {
        return errorLiveData
    }

}