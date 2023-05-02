package com.hvasoft.androidchallenge.domain.use_case

import com.hvasoft.androidchallenge.data.model.Comic
import com.hvasoft.androidchallenge.domain.ComicRepository
import com.hvasoft.androidchallenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetComicsByStartingTitleUseCase(
    private val repository: ComicRepository,
) {

    operator fun invoke(query: String): Flow<Resource<List<Comic>>> =
        repository.getComicsByStartingTitle(query)

}