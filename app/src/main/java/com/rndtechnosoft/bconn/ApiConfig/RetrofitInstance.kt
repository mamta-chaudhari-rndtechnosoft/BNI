package com.rndtechnosoft.bconn.ApiConfig

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    private const val BASE_URL = "http://bconn.rndtechnosoft.com/api/"

    private val gson = GsonBuilder().setLenient().create()

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}