package com.rndtechnosoft.bconn.Model

data class LoginResponseData(
    val message: String,
    val status: String,
    val token: String,
    val member: MemberData
)

data class MemberData(
    val _id: String,
    val bannerImg: String,
    val profileImg: String,
    val name: String,
    val email: String,
    val mobile: String,
    val country: String,
    val city: String,
    val refral_code: String,
    val approvedByadmin: String,
    val approvedBymember: String,
    val ref_member: String
)