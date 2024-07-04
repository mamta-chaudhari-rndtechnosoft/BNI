package com.rndtechnosoft.bni.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rndtechnosoft.bni.Model.LoginBody
import com.rndtechnosoft.bni.Model.LoginResponseData
import com.rndtechnosoft.bni.Repository.LoginRepository

class LoginViewModel():ViewModel() {

    private val loginResult = MutableLiveData<Result<LoginResponseData>>()
    private val loginRepository = LoginRepository()

    fun loginUser(loginBody: LoginBody){
        loginRepository.loginUser(loginBody).observeForever {
            loginResult.value = it
        }
    }

    fun observeLoginResult():MutableLiveData<Result<LoginResponseData>>{
        return loginResult
    }

}