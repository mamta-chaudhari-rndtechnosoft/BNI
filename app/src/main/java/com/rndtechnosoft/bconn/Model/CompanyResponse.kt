package com.rndtechnosoft.bconn.Model

data class CompanyResponse(
    val message: String,
    val companies: List<Company>
)

data class Company(
    val companyName: String
)