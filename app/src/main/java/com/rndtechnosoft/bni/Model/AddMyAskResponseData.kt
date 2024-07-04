package com.rndtechnosoft.bni.Model

import com.google.gson.annotations.SerializedName

data class AddMyAskResponseData(
    @SerializedName("data")
    val myAskData: MyAskData,
    val message: String,
    val status: String
)

data class MyAskData(
    @SerializedName("__v")
    val v: Int,
    @SerializedName("_id")
    val id: String,
    val companyName: String,
    val createdAt: String,
    val dept: String,
    val message: String,
    val updatedAt: String,
    val user: String
)