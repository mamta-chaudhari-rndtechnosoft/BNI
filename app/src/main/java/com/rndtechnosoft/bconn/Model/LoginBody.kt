package com.rndtechnosoft.bconn.Model

data class LoginBody(
    val email: String,
    val password: String,
    val deviceTokens:String
)