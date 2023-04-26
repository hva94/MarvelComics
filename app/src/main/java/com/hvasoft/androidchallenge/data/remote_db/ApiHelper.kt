package com.hvasoft.androidchallenge.data.remote_db

import com.hvasoft.androidchallenge.data.models.ComicDataWrapper
import retrofit2.Response

interface ApiHelper {
    suspend fun getComics():Response<ComicDataWrapper>
    suspend fun getComicsByStartingTitle(title: String):Response<ComicDataWrapper>
}