package com.hvasoft.androidchallenge.domain.use_case

import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.domain.repository.ComicRepository
import com.hvasoft.androidchallenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteComicsUseCase(
    private val repository: ComicRepository,
) {

    operator fun invoke(): Flow<Resource<List<Comic>>> {
        return repository.getFavoriteComics().map { comics ->
            Resource.success(comics.data?.sortedBy { it.title })
        }
    }

}