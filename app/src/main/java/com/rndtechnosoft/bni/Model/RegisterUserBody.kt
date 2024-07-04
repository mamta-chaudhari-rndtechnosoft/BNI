package com.rndtechnosoft.bni.Model

data class RegisterUserBody(
    val email: String,
    val name: String,
    val mobile: String,
    val password: String,
    val confirm_password: String,
    val country: String,
    val city: String,
    val chapter: String
)