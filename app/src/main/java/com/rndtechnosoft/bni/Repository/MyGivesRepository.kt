package com.rndtechnosoft.bni.Repository

import androidx.lifecycle.MutableLiveData
import com.rndtechnosoft.bni.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bni.Model.AddMyGivesBody
import com.rndtechnosoft.bni.Model.AddMyGivesResponseData
import com.rndtechnosoft.bni.Model.MyGivesData
import com.rndtechnosoft.bni.Model.MyGivesResponseData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyGivesRepository {

    private var errorLiveData = MutableLiveData<String>()
    fun addMyGives(
        addMyGivesBody: AddMyGivesBody,
        token: String
    ): MutableLiveData<Result<AddMyGivesResponseData>> {
        val addMyGiveValue = MutableLiveData<Result<AddMyGivesResponseData>>()

        RetrofitInstance.apiInterface.addMyGives(token, addMyGivesBody)
            .enqueue(object : Callback<AddMyGivesResponseData?> {
                override fun onResponse(
                    call: Call<AddMyGivesResponseData?>,
                    response: Response<AddMyGivesResponseData?>
                ) {
                    if (response.isSuccessful) {
                        addMyGiveValue.value = Result.success(response.body()!!)
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Response Not Success"
                        addMyGiveValue.value = Result.failure(Throwable(errorMessage))
                    }
                }

                override fun onFailure(call: Call<AddMyGivesResponseData?>, t: Throwable) {
                    addMyGiveValue.value = Result.failure(t)
                }
            })

        return addMyGiveValue
    }

    fun getMyGives(token: String, userId: String): MutableLiveData<MutableList<MyGivesData>> {

        val getMyGivesList = MutableLiveData<MutableList<MyGivesData>>()

        RetrofitInstance.apiInterface.getMyGives(token, userId)
            .enqueue(object : Callback<MyGivesResponseData?> {
                override fun onResponse(
                    call: Call<MyGivesResponseData?>,
                    response: Response<MyGivesResponseData?>
                ) {
                    if (response.isSuccessful) {
                        getMyGivesList.value = response.body()!!.data as MutableList
                    } else {
                        errorLiveData.value = "Response Not Successful: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<MyGivesResponseData?>, t: Throwable) {
                    errorLiveData.value = t.localizedMessage
                }
            })

        return getMyGivesList
    }

    fun getErrorLiveData(): MutableLiveData<String> {
        return errorLiveData
    }

}