package com.hvasoft.androidchallenge.domain.repository

import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.data.models.ComicDataWrapper
import com.hvasoft.androidchallenge.data.models.CreatorDataWrapper
import com.hvasoft.androidchallenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ComicRepository {

    fun getComics(): Flow<Resource<List<Comic>>>

    fun getComicsByStartingTitle(title: String): Flow<Resource<List<Comic>>>

    fun getComicDetail(comicId: String): Flow<Comic>

    suspend fun updateFavorite(comic: Comic): Int

    suspend fun getVariant(comicId: String): Response<ComicDataWrapper>

    suspend fun getThumbnailCreator(creatorId: String): Response<CreatorDataWrapper>

    fun getFavoriteComics(): Flow<Resource<List<Comic>>>

}