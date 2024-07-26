package com.rndtechnosoft.bni.Model

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    val currentPage: Int,
    @SerializedName("data")
    val countryData: List<CountryData>,
    val hasNextPage: Boolean,
    val message: String,
    val total: Int
)

data class CountryData(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val short_name:String,
    val name: String,
    val photo: List<String>,
    val updatedAt: String
)