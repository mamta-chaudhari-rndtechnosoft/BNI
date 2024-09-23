package com.rndtechnosoft.bconn.Model

import com.google.gson.annotations.SerializedName

data class ChapterResponse(
    val currentPage: Int,
    @SerializedName("data")
    val chapterData: List<ChapterData>,
    val hasNextPage: Boolean,
    val message: String,
    val total: Int
)
data class ChapterData(
    val __v: Int,
    val _id: String,
    val city: String,
    val countryName: String,
    val createdAt: String,
    val name: String,
    val updatedAt: String
)