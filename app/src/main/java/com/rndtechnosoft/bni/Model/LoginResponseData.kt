package com.rndtechnosoft.bni.Model

data class LoginResponseData(
    val userId: String,
    val message: String,
    val status: String,
    val token: String
)