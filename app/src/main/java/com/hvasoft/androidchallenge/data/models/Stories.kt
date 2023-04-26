package com.hvasoft.androidchallenge.data.models

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<StorySummary>,
    val returned: Int
)