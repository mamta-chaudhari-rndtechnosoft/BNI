package com.rndtechnosoft.bni.Model

import com.google.gson.annotations.SerializedName

data class IndustryResponseData(
    @SerializedName("data")
    val industryData: List<IndustryData>,
    val message: String
)

data class IndustryData(
    @SerializedName("__v")
    val v: Int,
    @SerializedName("_id")
    val id: String,
    val createdAt: String,
    val member: String,
    val name: String,
    val updatedAt: String
)