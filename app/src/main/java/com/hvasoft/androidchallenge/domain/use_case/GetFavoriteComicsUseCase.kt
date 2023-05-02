package com.hvasoft.androidchallenge.domain.use_case

import com.hvasoft.androidchallenge.data.model.Comic
import com.hvasoft.androidchallenge.domain.ComicRepository
import com.hvasoft.androidchallenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteComicsUseCase(
    private val repository: ComicRepository,
) {

    operator fun invoke(): Flow<Resource<List<Comic>>> =
        repository.getAllSavedComics().map { comics ->
            Resource.Success(comics.filter { it.isFavorite }.sortedBy { it.title })
        }
}