package com.rndtechnosoft.bni.Model

import com.google.gson.annotations.SerializedName

data class AddMyGivesResponseData(
    @SerializedName("data")
    val addMyGiveData: AddMyGiveData,
    val message: String,
    val status: String
)

data class AddMyGiveData(
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