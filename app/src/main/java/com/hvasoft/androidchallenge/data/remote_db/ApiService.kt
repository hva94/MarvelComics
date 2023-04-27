package com.hvasoft.androidchallenge.data.remote_db

import com.hvasoft.androidchallenge.core.Constants.GET_COMICS_PATH
import com.hvasoft.androidchallenge.core.Constants.GET_CREATORS_PATH
import com.hvasoft.androidchallenge.core.Constants.ID_PARAM
import com.hvasoft.androidchallenge.core.Constants.LIMIT_MIN_VALUE
import com.hvasoft.androidchallenge.core.Constants.LIMIT_PARAM
import com.hvasoft.androidchallenge.core.Constants.LIMIT_VALUE
import com.hvasoft.androidchallenge.core.Constants.NO_VARIANTS_PARAM
import com.hvasoft.androidchallenge.core.Constants.TITLE_STARTS_PARAM
import com.hvasoft.androidchallenge.data.models.ComicDataWrapper
import com.hvasoft.androidchallenge.data.models.CreatorDataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(GET_COMICS_PATH)
    suspend fun getComics(
        @Query(LIMIT_PARAM) limit: Int = LIMIT_VALUE,
    ): Response<ComicDataWrapper>

    @GET(GET_COMICS_PATH)
    suspend fun getComicsByStartingTitle(
        @Query(TITLE_STARTS_PARAM) titleStartsWith: String,
        @Query(NO_VARIANTS_PARAM) noVariants: Boolean = false,
        @Query(LIMIT_PARAM) limit: Int = LIMIT_VALUE
    ): Response<ComicDataWrapper>

    @GET(GET_COMICS_PATH)
    suspend fun getComicById(
        @Query(ID_PARAM) comicId: String,
        @Query(NO_VARIANTS_PARAM) noVariants: Boolean = true,
        @Query(LIMIT_PARAM) limit: Int = LIMIT_VALUE
    ): Response<ComicDataWrapper>

    @GET(GET_CREATORS_PATH)
    suspend fun getThumbnailCreator(
        @Query(ID_PARAM) creatorId: String,
        @Query(LIMIT_PARAM) limit: Int = LIMIT_MIN_VALUE
    ): Response<CreatorDataWrapper>

}