package com.hvasoft.androidchallenge.data.model

import com.google.gson.annotations.SerializedName

data class ComicDataWrapper(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    @SerializedName("data")
    val comicsData: ComicsData,
    val etag: String,
    val status: String
)