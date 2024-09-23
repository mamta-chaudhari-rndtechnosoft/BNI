package com.rndtechnosoft.bconn.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rndtechnosoft.bconn.Model.LoginBody
import com.rndtechnosoft.bconn.Model.LoginResponseData
import com.rndtechnosoft.bconn.Repository.LoginRepository

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