package com.rndtechnosoft.bconn.Model

data class USerInfoResponseData(
    val data: UserData
)
data class UserData(
    val _id: String,
    val approvedByadmin: String,
    val approvedBymember: String,
    val bannerImg: String,
    val city: String,
    val country: String,
    val deviceTokens: List<String>,
    val email: String,
    val mobile: String,
    val name: String,
    val password: String,
    val profileImg: String,
    val ref_member: String,
    val refral_code: String,
    val whatsapp:String,
    val facebook:String,
    val linkedin:String,
    val twitter:String
)