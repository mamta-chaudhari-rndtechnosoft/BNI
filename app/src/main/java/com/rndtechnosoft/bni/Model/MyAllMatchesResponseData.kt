package com.rndtechnosoft.bni.Model

data class MyAllMatchesResponseData(
    val matchedCompanies: List<MatchedCompany>
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
    val user: String,
    val webURL: String
)