package com.hvasoft.androidchallenge.domain.use_case

import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.domain.ComicRepository
import com.hvasoft.androidchallenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetComicsByStartingTitleUseCase(
    private val repository: ComicRepository,
) {

    operator fun invoke(query: String): Flow<Resource<List<Comic>>> {
        return repository.getComicsByStartingTitle(query)
            .map { comics ->
                val filteredComics = comics.data?.filter { comic ->
                    comic.title.contains(query, true)
                } ?: emptyList()
                Resource.success(filteredComics)
            }
    }

}