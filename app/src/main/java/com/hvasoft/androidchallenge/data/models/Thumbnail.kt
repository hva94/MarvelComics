package com.hvasoft.androidchallenge.data.models

data class Thumbnail(
    val extension: String,
    val path: String,
)

fun Thumbnail.url(): String {
    return "$path.$extension"
}