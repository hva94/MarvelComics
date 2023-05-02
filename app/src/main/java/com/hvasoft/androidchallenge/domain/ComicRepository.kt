package com.hvasoft.androidchallenge.domain

import com.hvasoft.androidchallenge.data.model.Comic
import com.hvasoft.androidchallenge.data.model.CreatorEntity
import com.hvasoft.androidchallenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ComicRepository {

    fun getComicsByStartingTitle(title: String): Flow<Resource<List<Comic>>>

    fun getComicById(id: String): Flow<Resource<Comic?>>

    fun getCreatorById(id: String): Flow<Resource<CreatorEntity?>>

    fun getAllSavedComics(): Flow<List<Comic>>

    suspend fun updateFavorite(comic: Comic): Int

}