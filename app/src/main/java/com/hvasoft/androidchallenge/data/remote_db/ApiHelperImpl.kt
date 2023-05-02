package com.hvasoft.androidchallenge.data.remote_db

import com.hvasoft.androidchallenge.data.model.ComicDataWrapper
import com.hvasoft.androidchallenge.data.model.CreatorDataWrapper
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService,
) : ApiHelper {

    override suspend fun getComicsByStartingTitle(title: String): Response<ComicDataWrapper> {
        return apiService.getComicsByStartingTitle(title)
    }

    override suspend fun getComicById(id: String): Response<ComicDataWrapper> {
        return apiService.getComicById(id)
    }

    override suspend fun getCreatorById(id: String): Response<CreatorDataWrapper> {
        return apiService.getCreatorById(id)
    }

}