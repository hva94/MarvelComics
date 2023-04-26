package com.hvasoft.androidchallenge.data.models

data class Variant(
    var id: String,
    val name: String,
    val resourceURI: String,
    var thumbnail: Thumbnail
)