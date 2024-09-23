package com.rndtechnosoft.bconn.Model

data class RegisterResponseData(
    val message: String,
    val status: String,
    val newMember:MemberResponse
)

data class MemberResponse(
    val bannerImg:String,
    val profileImg:String,
    val name:String,
    val email:String,
    val mobile:String,
    val country:String,
    val city:String,
    val refral_code:String,
    val password:String,
    val approvedByadmin:String,
    val approvedBymember:String,
    val ref_member:String,
    val _id:String

)