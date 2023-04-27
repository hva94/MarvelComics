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

    override suspend fun getComicById(id: String): Response<ComicDataWrapper> {
        return apiService.getComicById(id)
    }

    override suspend fun getThumbnailCreatorById(id: String): Response<CreatorDataWrapper> {
        return apiService.getThumbnailCreator(id)
    }
}