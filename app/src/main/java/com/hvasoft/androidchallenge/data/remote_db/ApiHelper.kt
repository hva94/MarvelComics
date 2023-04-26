package com.hvasoft.androidchallenge.data.remote_db

import com.hvasoft.androidchallenge.data.models.ComicDataWrapper
import com.hvasoft.androidchallenge.data.models.CreatorDataWrapper
import retrofit2.Response

interface ApiHelper {
    suspend fun getComics(): Response<ComicDataWrapper>
    suspend fun getComicsByStartingTitle(title: String): Response<ComicDataWrapper>
    suspend fun getVariant(comicId: String): Response<ComicDataWrapper>
    suspend fun getThumbnailCreator(creatorId: String): Response<CreatorDataWrapper>
}