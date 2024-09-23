package com.rndtechnosoft.bconn.Model

import com.google.gson.annotations.SerializedName

data class CityResponse(
    val _id: String,
    val name: String,
    val countryName: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

