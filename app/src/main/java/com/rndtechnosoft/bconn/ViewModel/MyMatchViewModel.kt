package com.rndtechnosoft.bconn.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rndtechnosoft.bconn.Model.Data
import com.rndtechnosoft.bconn.Model.MatchedCompany
import com.rndtechnosoft.bconn.Repository.MyMatchRepository

class MyMatchViewModel() : ViewModel() {

    private val repository = MyMatchRepository()
    private val myMatchAllData = MutableLiveData<MutableList<MatchedCompany>>()
    private val myMatchByCompany = MutableLiveData<MutableList<Data>>()
    private val errorData = MutableLiveData<String>()


    fun setMyAllMatch(token:String, userId:String){
        repository.myAllMatches(token,userId).observeForever {
            myMatchAllData.value = it
        }
    }

    fun getMyAllMatch():MutableLiveData<MutableList<MatchedCompany>>{
        return myMatchAllData
    }

    fun setMyAllMatchByCompany(token:String,userId: String,company:String,department:String){
        repository.myMatchByCompany(token,userId,company,department).observeForever {
            myMatchByCompany.value = it
        }
    }

    fun getMyAllMatchByCompany():MutableLiveData<MutableList<Data>>{
        return myMatchByCompany
    }

    /*fun setErrorData(){
        repository.errorData().observeForever {
            errorData.value = it
        }
    }*/

    fun getErrorData():MutableLiveData<String>{
        return errorData
    }

}