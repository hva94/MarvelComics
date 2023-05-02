package com.hvasoft.androidchallenge.data.model

data class ComicsData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Comic>,
    val total: Int
)