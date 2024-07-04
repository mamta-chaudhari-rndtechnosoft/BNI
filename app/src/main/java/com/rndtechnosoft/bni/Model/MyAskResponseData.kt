package com.rndtechnosoft.bni.Model

import com.google.gson.annotations.SerializedName
data class MyAskResponseData(
    //@SerializedName("data")
    val `data`: List<MyAskListData>,
    val status: String
)

data class MyAskListData(
    val __v: Int,
    val _id: String,
    val companyName: String,
    val createdAt: String,
    val dept: String,
    val message: String,
    val updatedAt: String,
    val user: String
)