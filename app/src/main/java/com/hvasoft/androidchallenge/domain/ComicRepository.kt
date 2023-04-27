package com.hvasoft.androidchallenge.domain

import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.data.models.ComicDataWrapper
import com.hvasoft.androidchallenge.data.models.Creator
import com.hvasoft.androidchallenge.data.models.CreatorDataWrapper
import com.hvasoft.androidchallenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ComicRepository {

    fun getComics(): Flow<Resource<List<Comic>>>

    fun getComicsByStartingTitle(title: String): Flow<Resource<List<Comic>>>

    fun getComicById(id: String): Flow<Comic>

    fun getFavoriteComics(): Flow<Resource<List<Comic>>>

    fun getCreatorById(id: String): Flow<Creator>

    suspend fun getThumbnailCreatorById(id: String): Response<CreatorDataWrapper>

    suspend fun fetchComicById(id: String): Response<ComicDataWrapper>

    suspend fun updateFavorite(comic: Comic): Int

}