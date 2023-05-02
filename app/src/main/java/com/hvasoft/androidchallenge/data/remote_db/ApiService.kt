package com.hvasoft.androidchallenge.data.remote_db

import com.hvasoft.androidchallenge.core.Constants.FORMAT_PARAM
import com.hvasoft.androidchallenge.core.Constants.FORMAT_TYPE_PARAM
import com.hvasoft.androidchallenge.core.Constants.FORMAT_TYPE_VALUE
import com.hvasoft.androidchallenge.core.Constants.FORMAT_VALUE
import com.hvasoft.androidchallenge.core.Constants.GET_COMICS_PATH
import com.hvasoft.androidchallenge.core.Constants.GET_CREATORS_PATH
import com.hvasoft.androidchallenge.core.Constants.ID_PARAM
import com.hvasoft.androidchallenge.core.Constants.LIMIT_MIN_VALUE
import com.hvasoft.androidchallenge.core.Constants.LIMIT_PARAM
import com.hvasoft.androidchallenge.core.Constants.LIMIT_VALUE
import com.hvasoft.androidchallenge.core.Constants.NO_VARIANTS_PARAM
import com.hvasoft.androidchallenge.core.Constants.ORDER_BY_PARAM
import com.hvasoft.androidchallenge.core.Constants.ORDER_BY_VALUE
import com.hvasoft.androidchallenge.core.Constants.TITLE_STARTS_PARAM
import com.hvasoft.androidchallenge.data.model.ComicDataWrapper
import com.hvasoft.androidchallenge.data.model.CreatorDataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(GET_COMICS_PATH)
    suspend fun getComicsByStartingTitle(
        @Query(TITLE_STARTS_PARAM) titleStartsWith: String,
        @Query(NO_VARIANTS_PARAM) noVariants: Boolean = false,
        @Query(FORMAT_PARAM) format: String = FORMAT_VALUE,
        @Query(FORMAT_TYPE_PARAM) formatType: String = FORMAT_TYPE_VALUE,
        @Query(ORDER_BY_PARAM) orderBy: String = ORDER_BY_VALUE,
        @Query(LIMIT_PARAM) limit: Int = LIMIT_VALUE
    ): Response<ComicDataWrapper>

    @GET(GET_COMICS_PATH)
    suspend fun getComicById(
        @Query(ID_PARAM) comicId: String,
        @Query(LIMIT_PARAM) limit: Int = LIMIT_MIN_VALUE
    ): Response<ComicDataWrapper>

    @GET(GET_CREATORS_PATH)
    suspend fun getCreatorById(
        @Query(ID_PARAM) creatorId: String,
        @Query(LIMIT_PARAM) limit: Int = LIMIT_MIN_VALUE
    ): Response<CreatorDataWrapper>

}