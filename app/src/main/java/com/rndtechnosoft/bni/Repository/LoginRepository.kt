package com.rndtechnosoft.bni.Repository

import androidx.lifecycle.MutableLiveData
import com.rndtechnosoft.bni.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bni.Model.LoginBody
import com.rndtechnosoft.bni.Model.LoginResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {

    fun loginUser(loginBody: LoginBody): MutableLiveData<Result<LoginResponseData>> {

        val loginResult = MutableLiveData<Result<LoginResponseData>>()

        RetrofitInstance.apiInterface.userLogin(loginBody)
            .enqueue(object : Callback<LoginResponseData?> {
                override fun onResponse(
                    call: Call<LoginResponseData?>,
                    response: Response<LoginResponseData?>
                ) {
                    if (response.isSuccessful) {
                        loginResult.value = Result.success(response.body()!!)
                    } else {
                        loginResult.value =
                            Result.failure(Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<LoginResponseData?>, t: Throwable) {
                    loginResult.value = Result.failure(t)
                }
            })
        return loginResult
    }

}