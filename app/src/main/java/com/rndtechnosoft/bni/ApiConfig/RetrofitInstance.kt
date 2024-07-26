package com.rndtechnosoft.bni.ApiConfig

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    //private const val BASE_URL = "https://newsapi.org/v2/"
    //private const val BASE_URL = "localhost:3002/"

    //ip a

    private const val BASE_URL = "http://bconn.rndtechnosoft.com/"
    //private const val BASE_URL = "https://bni-backend-w2c1.onrender.com/"

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