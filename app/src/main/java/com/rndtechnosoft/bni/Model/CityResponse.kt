package com.rndtechnosoft.bni.Model

import com.google.gson.annotations.SerializedName

data class CityResponse(
    val currentPage: Int,
    @SerializedName("data")
    val cityData: List<CityData>,
    val hasNextPage: Boolean,
    val message: String,
    val total: Int
)

data class CityData(
    val __v: Int,
    val _id: String,
    val countryName: String,
    val createdAt: String,
    val name: String,
    val updatedAt: String
)