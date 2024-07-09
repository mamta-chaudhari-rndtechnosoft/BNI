package com.rndtechnosoft.bni.Repository

import androidx.lifecycle.MutableLiveData
import com.rndtechnosoft.bni.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bni.Model.UpdateProfileBannerImageResponseData
import com.rndtechnosoft.bni.Model.UpdateProfileImageResponseData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageProfileRepository {

    private val errorLiveData = MutableLiveData<String>()

    fun updateProfile(
        token: String,
        profileImage: RequestBody
    ): MutableLiveData<Result<UpdateProfileImageResponseData>> {

        val responseValue = MutableLiveData<Result<UpdateProfileImageResponseData>>()

        RetrofitInstance.apiInterface.updateProfileImage(token, profileImage)
            .enqueue(object : Callback<UpdateProfileImageResponseData?> {
                override fun onResponse(
                    call: Call<UpdateProfileImageResponseData?>,
                    response: Response<UpdateProfileImageResponseData?>
                ) {
                    if (response.isSuccessful) {
                        responseValue.value = Result.success(response.body()!!)

                    } else {
                        errorLiveData.value = "Response Not Success: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<UpdateProfileImageResponseData?>, t: Throwable) {
                    errorLiveData.value = t.localizedMessage
                }
            })

        return responseValue
    }

   /* fun updateBanner(
        token: String,
        bannerImage: RequestBody
    ): MutableLiveData<Result<UpdateProfileBannerImageResponseData>> {

        val responseValue = MutableLiveData<Result<UpdateProfileBannerImageResponseData>>()

        RetrofitInstance.apiInterface.updateBanner(token, bannerImage)
            .enqueue(object : Callback<UpdateProfileBannerImageResponseData?> {
                override fun onResponse(
                    call: Call<UpdateProfileBannerImageResponseData?>,
                    response: Response<UpdateProfileBannerImageResponseData?>
                ) {
                    if (response.isSuccessful) {
                        responseValue.value = Result.success(response.body()!!)

                    } else {
                        errorLiveData.value = "Response Not Success: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<UpdateProfileBannerImageResponseData?>, t: Throwable) {
                    errorLiveData.value = t.localizedMessage
                }
            })

        return responseValue
    }*/

    fun getErrorLiveData(): MutableLiveData<String> {
        return errorLiveData
    }

}