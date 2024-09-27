package com.rndtechnosoft.bconn.Model

data class MyAllMatchesResponseData(
    val data: List<MatchedCompany>
)
data class MatchedCompany(
    val __v: Int,
    val _id: String,
    val companyName: String,
    val createdAt: String,
    val dept: String,
    val email: String,
    val phoneNumber: String,
    val updatedAt: String,
    val user: User,
    val webURL: String
)
data class User(
    val _id:String,
    val name:String,
    val email:String,
    val mobile:String
)