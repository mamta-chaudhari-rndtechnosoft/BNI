package com.rndtechnosoft.bconn.Model

data class BusinessListResponseData(
    val data: List<BusinessListData>,
    val message:String
)

data class BusinessListData(

    val _id:String,
    val companyName:String,
    val industryName:String,
    val whatsapp:String,
    val designation:String,
    val aboutCompany:String,
    val companyAddress:String,
    val user:UserCompany,
    val catalog:String

)
data class UserCompany(
    val _id:String,
    val name:String
)
