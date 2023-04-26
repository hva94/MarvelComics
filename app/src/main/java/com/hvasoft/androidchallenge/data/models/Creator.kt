package com.hvasoft.androidchallenge.data.models

data class Creator(
    val name: String,
    val resourceURI: String,
    val role: String,
    var thumbnail: Thumbnail? = null
)