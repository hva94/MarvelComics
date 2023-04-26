package com.hvasoft.androidchallenge.data.models

data class Creators(
    val available: Int,
    val collectionURI: String,
    val items: List<CharacterSummary>,
    val returned: Int
)