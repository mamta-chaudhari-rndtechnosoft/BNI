package com.rndtechnosoft.bconn.Model

data class RegisterUserBody(
    val name: String,
    val email: String,
    val mobile: String,
    val country: String,
    val city: String,
    val password: String,
    val confirm_password: String,
    val ref_member: String
)