package com.hvasoft.androidchallenge.data.models

data class ComicsData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Comic>,
    val total: Int
)