package com.hvasoft.androidchallenge.data.remote_db

import com.hvasoft.androidchallenge.data.model.ComicDataWrapper
import com.hvasoft.androidchallenge.data.model.CreatorDataWrapper
import retrofit2.Response

interface ApiHelper {

    suspend fun getComicsByStartingTitle(title: String): Response<ComicDataWrapper>

    suspend fun getComicById(id: String): Response<ComicDataWrapper>

    suspend fun getCreatorById(id: String): Response<CreatorDataWrapper>

}