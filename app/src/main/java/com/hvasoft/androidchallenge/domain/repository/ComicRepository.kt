package com.hvasoft.androidchallenge.domain.repository

import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ComicRepository {
    fun getComics(): Flow<Resource<List<Comic>>>
    fun getComicsByStartingTitle(title: String): Flow<Resource<List<Comic>>>
    fun getComicDetail(comicId: String): Flow<Comic>
}