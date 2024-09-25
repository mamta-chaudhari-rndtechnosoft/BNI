package com.rndtechnosoft.bconn.ApiConfig

import com.rndtechnosoft.bconn.Model.AddMyAskBody
import com.rndtechnosoft.bconn.Model.AddMyAskResponseData
import com.rndtechnosoft.bconn.Model.AddMyGivesBody
import com.rndtechnosoft.bconn.Model.AddMyGivesResponseData
import com.rndtechnosoft.bconn.Model.BusinessListResponseData
import com.rndtechnosoft.bconn.Model.ChapterResponse
import com.rndtechnosoft.bconn.Model.CityResponse
import com.rndtechnosoft.bconn.Model.ContactLinksProfileBody
import com.rndtechnosoft.bconn.Model.CountryResponse
import com.rndtechnosoft.bconn.Model.IndustryResponseData
import com.rndtechnosoft.bconn.Model.LoginBody
import com.rndtechnosoft.bconn.Model.LoginResponseData
import com.rndtechnosoft.bconn.Model.MemberVerifiedResponseData
import com.rndtechnosoft.bconn.Model.MyAllMatchesResponseData
import com.rndtechnosoft.bconn.Model.MyAskResponseData
import com.rndtechnosoft.bconn.Model.MyGivesResponseData
import com.rndtechnosoft.bconn.Model.MyMatchByCompaniesResponseData
import com.rndtechnosoft.bconn.Model.RegisterResponseData
import com.rndtechnosoft.bconn.Model.RegisterUserBody
import com.rndtechnosoft.bconn.Model.UpdateProfileBannerImageResponseData
import com.rndtechnosoft.bconn.Model.UpdateProfileImageResponseData
import okhttp3.MultipartBody
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
    @GET("city/getCityByCountry")
    fun getCity(@Query("countryName") countryName: String): Call<List<CityResponse>>

    //later do with id
    @GET("chapter/getChapterByCity")
    fun getChapter(@Query("city") city: String): Call<ChapterResponse>

    @POST("member/register")
    fun userRegister(@Body registerUserBody: RegisterUserBody): Call<RegisterResponseData>

    @POST("member/login")
    fun userLogin(@Body loginBody: LoginBody): Call<LoginResponseData>

    @POST("myAsk/addMyAsk")
    fun addMyAsk(
        @Header("authorization") authorization: String,
        @Query("user") userId:String,
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
        @Query("user") userId:String,
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
    @PUT("business/updateBusinessById")
    fun updateBanner(
        @Header("authorization") authorization: String,
        @Query("id") userId: String?,
        @Part image: MultipartBody.Part,
    ): Call<UpdateProfileBannerImageResponseData>

    @Multipart
    @POST("business/create")
    fun createBusiness(
        @Header("authorization") authorization: String,
        @Part("designation") designation: RequestBody,
        @Part("aboutCompany") aboutCompany: RequestBody,
        @Part("companyAddress") companyAddress: RequestBody,
        @Part("contactLinks[whatsapp]") whatsAppContactLink: RequestBody
    ): Call<ResponseBody>

    @GET("industry/getAllIndustry")
    fun getAllIndustry(): Call<IndustryResponseData>

    @PUT("business/updateContactLinks")
    fun updateContactLinks(
        @Header("authorization") authorization: String,
        @Body contactLinksProfileBody: ContactLinksProfileBody
    ): Call<ResponseBody>

    @GET("business/businesssList")
    fun businessList(@Header("authorization") authorization: String): Call<List<BusinessListResponseData>>

    //https://bconn.rndtechnosoft.com/api/member/isMemberVerify?id=66f0f6b2da1d20441d068a42
    @GET("member/isMemberVerify")
    fun memberVerified(@Query("id") id:String):Call<MemberVerifiedResponseData>



}