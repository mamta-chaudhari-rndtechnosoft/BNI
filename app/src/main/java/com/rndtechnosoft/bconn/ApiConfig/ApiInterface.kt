package com.rndtechnosoft.bconn.ApiConfig

import com.rndtechnosoft.bconn.Model.AddBusinessBody
import com.rndtechnosoft.bconn.Model.AddCatalogResponseData
import com.rndtechnosoft.bconn.Model.AddMyAskBody
import com.rndtechnosoft.bconn.Model.AddMyAskResponseData
import com.rndtechnosoft.bconn.Model.AddMyGivesBody
import com.rndtechnosoft.bconn.Model.AddMyGivesResponseData
import com.rndtechnosoft.bconn.Model.BusinessInfoResponseData
import com.rndtechnosoft.bconn.Model.BusinessListResponseData
import com.rndtechnosoft.bconn.Model.ChapterResponse
import com.rndtechnosoft.bconn.Model.CityResponse
import com.rndtechnosoft.bconn.Model.CompanyResponse
import com.rndtechnosoft.bconn.Model.ContactLinksProfileBody
import com.rndtechnosoft.bconn.Model.CountryResponse
import com.rndtechnosoft.bconn.Model.DepartmentListResponseData
import com.rndtechnosoft.bconn.Model.IndustryResponseData
import com.rndtechnosoft.bconn.Model.LoginBody
import com.rndtechnosoft.bconn.Model.LoginResponseData
import com.rndtechnosoft.bconn.Model.MemberVerifiedResponseData
import com.rndtechnosoft.bconn.Model.MyAllMatchesResponseData
import com.rndtechnosoft.bconn.Model.MyAskResponseData
import com.rndtechnosoft.bconn.Model.MyGivesResponseData
import com.rndtechnosoft.bconn.Model.MyMatchByCompaniesResponseData
import com.rndtechnosoft.bconn.Model.ReferralMembersResponseData
import com.rndtechnosoft.bconn.Model.RegisterResponseData
import com.rndtechnosoft.bconn.Model.RegisterUserBody
import com.rndtechnosoft.bconn.Model.USerInfoResponseData
import com.rndtechnosoft.bconn.Model.UpdateMemberStatusBody
import com.rndtechnosoft.bconn.Model.UpdateMemberStatusResponseData
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
        @Query("user") userId: String,
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
        @Query("user") userId: String,
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

    @GET("match2/myMatchesByCompanyAndDept")
    fun myMatchByCompany(
        @Header("authorization") authorization: String,
        @Query("companyName") companyName: String?,
        @Query("dept") department: String?
    ): Call<MyMatchByCompaniesResponseData>

    //update profile image
    @Multipart
    @PUT("member/updatememberById")
    fun updateProfileImageUser(
        @Header("authorization") authorization: String,
        @Query("id") userId: String?,
        @Part image: MultipartBody.Part,
    ): Call<UpdateProfileImageResponseData>

    //update banner image
    @Multipart
    @PUT("member/updatememberById")
    fun updateBannerUser(
        @Header("authorization") authorization: String,
        @Query("id") userId: String?,
        @Part image: MultipartBody.Part,
    ): Call<UpdateProfileBannerImageResponseData>

    // Update user contact links
    @Multipart
    @PUT("member/updatememberById")
    fun updateUserContactLinks(
        @Header("authorization") authorization: String,
        @Query("id") userId: String?,
        @Part("name") name:RequestBody?,
        @Part("mobile") number: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("facebook") facebook: RequestBody?,
        @Part("linkedin") linkedin: RequestBody?,
    ): Call<ResponseBody>


    @Multipart
    @PUT("business/updateBusinessById")
    fun updateProfileImageBusiness(
        @Header("authorization") authorization: String,
        @Query("id") userId: String?,
        @Part image: MultipartBody.Part,
    ): Call<UpdateProfileImageResponseData>

    @Multipart
    @PUT("business/updateBusinessById")
    fun updateBannerUserBusiness(
        @Header("authorization") authorization: String,
        @Query("id") userId: String?,
        @Part image: MultipartBody.Part,
    ): Call<UpdateProfileBannerImageResponseData>

    @Multipart
    @PUT("business/updateBusinessById")
    fun updateBusinessContactLinks(
        @Header("authorization") authorization: String,
        @Query("id") userId: String?,
        @Part("mobile") number: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("facebook") facebook: RequestBody?,
        @Part("linkedin") linkedin: RequestBody?,
    ): Call<ResponseBody>

    @Multipart
    @PUT("business/updateBusinessById")
    fun updateBusinessDetails(
        @Header("authorization") authorization: String,
        @Query("id") userId: String?,
        @Part("companyName") companyName: RequestBody?,
        @Part("industryName") industryName: RequestBody?,
        @Part("designation") designation: RequestBody?,
        @Part("aboutCompany") aboutCompany: RequestBody?,
        @Part("companyAddress") companyAddress: RequestBody?,
    ): Call<ResponseBody>

    @Multipart
    @POST("business/createProfile")
    fun createBusiness(
        @Header("authorization") authorization: String,
        @Query("userId") userId: String,
        @Part("companyName") companyName: RequestBody,
        @Part("email") companyEmail: RequestBody,
        @Part("mobile") companyNumber: RequestBody,
        @Part("industryName") industryName: RequestBody,
        @Part("designation") designation: RequestBody,
        @Part("companyAddress") companyAddress: RequestBody,
        @Part("aboutCompany") aboutCompany: RequestBody
    ): Call<ResponseBody>

    //http://bconn.rndtechnosoft.com/business/createProfile?user=66850d7492dc8ac40f9bb7b7
    @POST("business/createProfile")
    fun addBusiness(
        @Header("authorization") authorization: String,
        @Query("user") userId: String,
        @Body addBusinessBody: AddBusinessBody
    ): Call<ResponseBody>

    @GET("business/getBusinessBymyId")
    fun getBusinessInfo(
        @Header("authorization") authorization: String,
        @Query("id") businessId: String,
    ): Call<BusinessInfoResponseData>

    @GET("industry/getAllIndustry")
    fun getAllIndustry(
        @Header("authorization") authorization: String
    ): Call<IndustryResponseData>

    @PUT("business/updateContactLinks")
    fun updateContactLinks(
        @Header("authorization") authorization: String,
        @Body contactLinksProfileBody: ContactLinksProfileBody
    ): Call<ResponseBody>

    @GET("business/getbusinessByuserId")
    fun businessList(
        @Header("authorization") authorization: String,
        @Query("userId") businessId: String,
    ): Call<BusinessListResponseData>

    @GET("member/isMemberVerify")
    fun memberVerified(@Query("id") id: String): Call<MemberVerifiedResponseData>

    //status are: pending,cancel, approved
    @GET("member/getPendingMember")
    fun getReferralMembers(
        @Header("authorization") authorization: String,
        @Query("refMember") refMember: String,
        @Query("status") status: String
    ): Call<ReferralMembersResponseData>


    @PUT("member/updatememberById")
    fun updateMemberStatus(
        @Header("authorization") authorization: String,
        @Query("id") id: String,
        @Body updateMemberStatusBody: UpdateMemberStatusBody
    ): Call<UpdateMemberStatusResponseData>


    @GET("department/getAllDepartment")
    fun getAllDepartment(
        @Header("authorization") authorization: String
    ): Call<DepartmentListResponseData>

    @GET("member/getUserById")
    fun getUserInfo(
        @Header("authorization") authorization: String,
        @Query("id") id: String
    ): Call<USerInfoResponseData>

    @GET("/api/company/getFilteredGives")
    fun getFilteredGives(
        @Header("Authorization") token: String,
        @Query("companyName") companyName: String
    ): Call<CompanyResponse>

    @Multipart
    @PUT("business/updateBusinessById")
    fun addCatalogue(
        @Header("authorization") authorization: String,
        @Query("id") userId: String?,
        @Part filePdf: MultipartBody.Part,
    ): Call<AddCatalogResponseData>



}