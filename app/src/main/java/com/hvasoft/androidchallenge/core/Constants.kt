package com.hvasoft.androidchallenge.core

import java.sql.Timestamp

object Constants {

    const val BASE_URL = "https://gateway.marvel.com/"
    const val GET_PATH = "v1/public/comics"
    val TS_PARAM = Timestamp(System.currentTimeMillis()).time.toString()
    const val LIMIT_PARAM = "limit"
    const val LIMIT_VALUE = 50
    const val TITLE_STARTS_PARAM = "titleStartsWith"
    const val PUBLIC_KEY: String = "443fd5d8c265e6f3918267e51dd21653"
    const val PRIVATE_KEY: String = "f811ca74bc3c9cacfd67c90db09d1bcf7a3808f5"

    const val DATABASE_NAME = "comics"
    const val TABLE_NAME_COMICS = "comics"
}