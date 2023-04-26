package com.hvasoft.androidchallenge.data.models

data class CreatorsData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Creator>,
    val total: Int
)