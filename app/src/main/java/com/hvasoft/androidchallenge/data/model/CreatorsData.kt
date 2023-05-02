package com.hvasoft.androidchallenge.data.model

data class CreatorsData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<CreatorEntity>,
    val total: Int
)