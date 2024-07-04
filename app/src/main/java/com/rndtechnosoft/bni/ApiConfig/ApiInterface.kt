package com.rndtechnosoft.bni.ApiConfig

import com.rndtechnosoft.bni.Model.AddMyAskBody
import com.rndtechnosoft.bni.Model.AddMyAskResponseData
import com.rndtechnosoft.bni.Model.AddMyGivesBody
import com.rndtechnosoft.bni.Model.AddMyGivesResponseData
import com.rndtechnosoft.bni.Model.ChapterResponse
import com.rndtechnosoft.bni.Model.CityResponse
import com.rndtechnosoft.bni.Model.CountryResponse
import com.rndtechnosoft.bni.Model.LoginBody
import com.rndtechnosoft.bni.Model.LoginResponseData
import com.rndtechnosoft.bni.Model.MyAllMatchesResponseData
import com.rndtechnosoft.bni.Model.MyAskResponseData
import com.rndtechnosoft.bni.Model.MyGivesResponseData
import com.rndtechnosoft.bni.Model.MyMatchByCompaniesResponseData
import com.rndtechnosoft.bni.Model.RegisterResponseData
import com.rndtechnosoft.bni.Model.RegisterUserBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET("country/getCountry")
    fun getCountry(): Call<CountryResponse>

    //later do with id
    @GET("city/getCity")
    fun getCity(): Call<CityResponse>

    //later do with id
    @GET("chapter/getchapter")
    fun getChapter(): Call<ChapterResponse>

    @POST("member/register")
    fun userRegister(@Body registerUserBody: RegisterUserBody): Call<RegisterResponseData>

    @POST("member/login")
    fun userLogin(@Body loginBody: LoginBody): Call<LoginResponseData>

    @POST("myAsk/addMyAsk")
    fun addMyAsk(
        @Header("authorization") authorization: String,
        @Body addMyAskBody: AddMyAskBody
    ): Call<AddMyAskResponseData>

    @GET("myAsk/getMyAsk")
    fun myAskList(
        @Header("authorization") authorization: String,
        @Query("userId") userId: String?
    ): Call<MyAskResponseData>

    @POST("myGives/addMyGives")
    fun addMyGives(
        @Header("authorization") authorization: String,
        @Body addMyGivesBody: AddMyGivesBody
    ): Call<AddMyGivesResponseData>

    @GET("myGives/getMyGives")
    fun getMyGives(
        @Header("authorization") authorization: String,
        @Query("userId") userId: String?
    ): Call<MyGivesResponseData>


    @GET("match2/myAllMatches")
    fun myAllMatches(
        @Header("authorization") authorization: String,
        @Query("userId") userId: String?
    ): Call<MyAllMatchesResponseData>

    @GET("myGives/getAllMyMatchs")
    fun myMatchByCompany(
        @Header("authorization") authorization: String,
        @Query("userId") userId: String?,
        @Query("companyName") companyName: String?,
        @Query("dept") department: String?
    ): Call<MyMatchByCompaniesResponseData>

}