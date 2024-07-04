package com.rndtechnosoft.bni.Repository

import androidx.lifecycle.MutableLiveData
import com.rndtechnosoft.bni.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bni.Model.AddMyAskBody
import com.rndtechnosoft.bni.Model.AddMyAskResponseData
import com.rndtechnosoft.bni.Model.MyAskData
import com.rndtechnosoft.bni.Model.MyAskListData
import com.rndtechnosoft.bni.Model.MyAskResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAskRepository {

    private val errorLiveData = MutableLiveData<String>()
    fun addMyAsk(addMyAskBody: AddMyAskBody,token:String): MutableLiveData<Result<AddMyAskResponseData>> {

        val askValue = MutableLiveData<Result<AddMyAskResponseData>>()

        RetrofitInstance.apiInterface.addMyAsk(token,addMyAskBody)
            .enqueue(object : Callback<AddMyAskResponseData?> {
                override fun onResponse(
                    call: Call<AddMyAskResponseData?>,
                    response: Response<AddMyAskResponseData?>
                ) {
                    if (response.isSuccessful) {
                        when(response.code()){
                            201 -> {
                                askValue.value = Result.success(response.body()!!)
                            }
                        }


                    } else {
                        //askValue.value = Result.failure(Throwable(response.errorBody()?.string()))

                        val errorMessage = response.errorBody()?.string() ?: "Response Not Success"
                        askValue.value = Result.failure(Throwable(errorMessage))

                    }
                }

                override fun onFailure(call: Call<AddMyAskResponseData?>, t: Throwable) {
                    askValue.value = Result.failure(t)
                }
            })

        return askValue
    }

    fun myAsk(userId: String,token:String): MutableLiveData<MutableList<MyAskListData>> {

        val myAskListResult = MutableLiveData<MutableList<MyAskListData>>()

        RetrofitInstance.apiInterface.myAskList(token,userId)
            .enqueue(object : Callback<MyAskResponseData?> {
                override fun onResponse(
                    call: Call<MyAskResponseData?>,
                    response: Response<MyAskResponseData?>
                ) {
                    if (response.isSuccessful) {
                        //myAskListResult.value = Result.success(response.body()!!.myAskListData as MutableList

                        myAskListResult.value = response.body()!!.data as MutableList
                        //Result.success(response.body()!!.myAskListData as MutableList)

                    } else {
                        errorLiveData.value = "Response not successful :("
                    }
                }

                override fun onFailure(call: Call<MyAskResponseData?>, t: Throwable) {
                    errorLiveData.value = t.localizedMessage
                }
            })
        return myAskListResult
    }

    fun getErrorLiveData(): MutableLiveData<String> {
        return errorLiveData
    }

}