package com.rndtechnosoft.bni.Model

import com.google.gson.annotations.SerializedName

data class MyGivesResponseData(
    //@SerializedName("data")
    val `data`: List<MyGivesData>,
    val message: String
)

data class MyGivesData(
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