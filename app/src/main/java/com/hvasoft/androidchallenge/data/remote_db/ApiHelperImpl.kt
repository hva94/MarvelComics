package com.hvasoft.androidchallenge.data.remote_db

import com.hvasoft.androidchallenge.data.models.ComicDataWrapper
import com.hvasoft.androidchallenge.data.models.CreatorDataWrapper
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService,
) : ApiHelper {

    override suspend fun getComics(): Response<ComicDataWrapper> = apiService.getComics()

    override suspend fun getComicsByStartingTitle(title: String): Response<ComicDataWrapper> {
        return apiService.getComicsByStartingTitle(title)
    }

    override suspend fun getVariant(comicId: String): Response<ComicDataWrapper> {
        return apiService.getVariant(comicId)
    }

    override suspend fun getThumbnailCreator(creatorId: String): Response<CreatorDataWrapper> {
        return apiService.getThumbnailCreator(creatorId)
    }
}