package com.hvasoft.androidchallenge.core

import java.sql.Timestamp

object Constants {

    const val BASE_URL = "https://gateway.marvel.com/"
    const val GET_COMICS_PATH = "v1/public/comics"
    const val GET_CREATORS_PATH = "v1/public/creators"
    val TS_PARAM = Timestamp(System.currentTimeMillis()).time.toString()
    const val LIMIT_PARAM = "limit"
    const val LIMIT_VALUE = 5
    const val LIMIT_MIN_VALUE = 1
    const val TITLE_STARTS_PARAM = "titleStartsWith"
    const val ID_PARAM = "id"
    const val ORDER_BY_PARAM = "modified"
    const val NO_VARIANTS_PARAM = "noVariants"
    const val PUBLIC_KEY_PARAM: String = "443fd5d8c265e6f3918267e51dd21653"
    const val PRIVATE_KEY_PARAM: String = "f811ca74bc3c9cacfd67c90db09d1bcf7a3808f5"

    const val DATABASE_NAME = "comics_db"
    const val TABLE_NAME_COMICS = "comics"
}