package com.rndtechnosoft.bni.Repository

import androidx.lifecycle.MutableLiveData
import com.rndtechnosoft.bni.ApiConfig.ApiInterface
import com.rndtechnosoft.bni.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bni.Model.ChapterData
import com.rndtechnosoft.bni.Model.ChapterResponse
import com.rndtechnosoft.bni.Model.CityData
import com.rndtechnosoft.bni.Model.CityResponse
import com.rndtechnosoft.bni.Model.CountryData
import com.rndtechnosoft.bni.Model.CountryResponse
import com.rndtechnosoft.bni.Model.RegisterResponseData
import com.rndtechnosoft.bni.Model.RegisterUserBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistrationRepository private constructor() {

    private val apiInterface: ApiInterface = RetrofitInstance.apiInterface

    companion object {
        @Volatile
        private var instance: RegistrationRepository? = null

        fun getInstance(): RegistrationRepository {
            return instance ?: synchronized(this) {
                instance ?: RegistrationRepository().also { instance = it }
            }
        }
    }

    fun getCountries(): MutableLiveData<List<CountryData>> {
        val data = MutableLiveData<List<CountryData>>()

        apiInterface.getCountry().enqueue(object : Callback<CountryResponse?> {
            override fun onResponse(
                call: Call<CountryResponse?>,
                response: Response<CountryResponse?>
            ) {
                if (response.isSuccessful) {
                    /* response.body()?.let {
                         data.value = it.countryData

                     } ?: run {
                         data.value = emptyList()
                     }*/

                    data.value = response.body()!!.countryData

                } else {
                    data.value = emptyList()
                }
            }

            override fun onFailure(call: Call<CountryResponse?>, t: Throwable) {
                data.value = emptyList()
            }
        })

        return data
    }

    fun getCity(): MutableLiveData<List<CityData>> {
        val data = MutableLiveData<List<CityData>>()

        apiInterface.getCity().enqueue(object : Callback<CityResponse?> {
            override fun onResponse(call: Call<CityResponse?>, response: Response<CityResponse?>) {
                if (response.isSuccessful) {
                    data.value = response.body()!!.cityData
                } else {
                    data.value = emptyList()
                }
            }

            override fun onFailure(call: Call<CityResponse?>, t: Throwable) {
                data.value = emptyList()
            }
        })
        return data
    }

    fun getChapter(): MutableLiveData<List<ChapterData>> {
        val data = MutableLiveData<List<ChapterData>>()
        apiInterface.getChapter().enqueue(object : Callback<ChapterResponse?> {
            override fun onResponse(
                call: Call<ChapterResponse?>,
                response: Response<ChapterResponse?>
            ) {
                if (response.isSuccessful) {
                    data.value = response.body()!!.chapterData
                } else {
                    data.value = emptyList()
                }
            }

            override fun onFailure(call: Call<ChapterResponse?>, t: Throwable) {
                data.value = emptyList()
            }
        })
        return data
    }

    fun registerUser(registerUserBody: RegisterUserBody): MutableLiveData<Result<RegisterResponseData>> {
        val result = MutableLiveData<Result<RegisterResponseData>>()
        apiInterface.userRegister(registerUserBody)
            .enqueue(object : Callback<RegisterResponseData?> {
                override fun onResponse(
                    call: Call<RegisterResponseData?>,
                    response: Response<RegisterResponseData?>
                ) {
                    if (response.isSuccessful) {
                        result.value = Result.success(response.body()!!)
                    } else {
                        result.value = Result.failure(Throwable(response.errorBody()?.string()))
                    }
                }
                override fun onFailure(call: Call<RegisterResponseData?>, t: Throwable) {
                    result.value = Result.failure(t)
                }
            })
        return result
    }


}