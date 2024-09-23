package com.rndtechnosoft.bconn.Model

data class MyMatchByCompaniesResponseData(

    val `data`: List<Data>,
    val status: String
)

data class Data(
    val myGives: MyGives
)

data class MyGives(
    val _id: String,
    val companyName: String,
    val createdAt: String,
    val dept: String,
    val email: String,
    val phoneNumber: String,
    val updatedAt: String,
    val user: String,
    val webURL: String
)