package com.rndtechnosoft.bconn.Model

data class MyMatchByCompaniesResponseData(

    val `data`: List<Data>,
    //val status: String
)

data class Data(
    val _id: String,
    val companyName: String,
    val createdAt: String,
    val dept: String,
    val email: String,
    val phoneNumber: String,
    val updatedAt: String,
    val user: UserMyMatch,
    val webURL: String
)

data class UserMyMatch(
    val _id: String,
    val name: String,
    val email: String,
    val mobile: String,
    val country: String,
    val city: String
)