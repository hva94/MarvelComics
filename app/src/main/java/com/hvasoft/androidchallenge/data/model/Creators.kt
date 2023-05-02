package com.hvasoft.androidchallenge.data.model

data class Creators(
    val available: Int,
    val collectionURI: String,
    var items: List<Creator>,
    val returned: Int
)