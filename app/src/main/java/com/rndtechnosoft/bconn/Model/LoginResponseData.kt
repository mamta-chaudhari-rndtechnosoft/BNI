package com.rndtechnosoft.bconn.Model

data class LoginResponseData(
    val userId: String,
    val message: String,
    val status: String,
    val token: String
)