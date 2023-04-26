package com.hvasoft.androidchallenge.data.models

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)