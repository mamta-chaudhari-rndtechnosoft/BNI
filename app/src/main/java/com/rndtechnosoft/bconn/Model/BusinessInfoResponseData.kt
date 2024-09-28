package com.rndtechnosoft.bconn.Model

class BusinessInfoResponseData(
    val data: BusinessData,
    val message:String
)

class BusinessData(
    val _id:String,
    val bannerImg:String,
    val profileImg:String,
    val industryName:String,
    val whatsapp:String,
    val designation:String,
    val aboutCompany:String,
    val companyName:String,
    val companyAddress:String,
    val user:String,
    val catalog:String,
    val facebook:String,
    val linkedin:String,
    val email:String,
    val mobile:String
)