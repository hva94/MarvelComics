package com.hvasoft.androidchallenge.data.remote_db

import com.hvasoft.androidchallenge.core.Constants.GET_PATH
import com.hvasoft.androidchallenge.core.Constants.LIMIT_PARAM
import com.hvasoft.androidchallenge.core.Constants.LIMIT_VALUE
import com.hvasoft.androidchallenge.core.Constants.TITLE_STARTS_PARAM
import com.hvasoft.androidchallenge.data.models.ComicDataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(GET_PATH)
    suspend fun getComics(
        @Query(LIMIT_PARAM) limit: Int = LIMIT_VALUE,
    ): Response<ComicDataWrapper>

    @GET(GET_PATH)
    suspend fun getComicsByStartingTitle(
        @Query(TITLE_STARTS_PARAM) titleStartsWith: String,
        @Query(LIMIT_PARAM) limit: Int = LIMIT_VALUE
    ): Response<ComicDataWrapper>
}