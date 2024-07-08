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
import com.rndtechnosoft.bni.Model.UpdateProfileBannerImageResponseData
import com.rndtechnosoft.bni.Model.UpdateProfileImageResponseData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @Multipart
    @PUT("business/updateImg")
    fun updateProfileImage(
        @Header("authorization") authorization: String,
        @Part("profileImg") profileImage: RequestBody?
    ): Call<UpdateProfileImageResponseData>

    @Multipart
    @PUT("business/updateImg")
    fun updateBanner(
        @Header("authorization") authorization: String,
        @Part("bannerImg") bannerImage: RequestBody?
    ): Call<UpdateProfileBannerImageResponseData>


    /*
    @Multipart
    @POST("customer_kyc")
    fun customerKyc(
        @Part("id") id: RequestBody,
        @Part("bank_name") bankName: RequestBody,
        @Part("account_no") accountNumber: RequestBody,
        @Part("ifsc") ifsc: RequestBody,
        @Part("aadhar_card") imageAadhaar: RequestBody?,
        @Part("aadharcard_back_image") imageAadhaarBack: RequestBody?,
        @Part("pan_card") imagePan: RequestBody?,
        @Part("passbook_image") imagePassBook: RequestBody?,
        @Part("cheque_image") imageCheque: RequestBody?
    ): Call<ResponseBody>
    */

}