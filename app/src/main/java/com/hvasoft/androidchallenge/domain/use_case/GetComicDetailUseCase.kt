package com.hvasoft.androidchallenge.domain.use_case

import com.hvasoft.androidchallenge.data.model.Comic
import com.hvasoft.androidchallenge.data.model.Creator
import com.hvasoft.androidchallenge.domain.ComicRepository
import com.hvasoft.androidchallenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetComicDetailUseCase(
    private val repository: ComicRepository,
) {

    operator fun invoke(id: String): Flow<Resource<Comic>> =
        repository.getComicById(id).map { comic ->
            if (comic.data?.description.isNullOrEmpty()) {
                comic.data?.description = "This comic has no description."
            }
            val creatorList = comic.data?.creators?.items
            if (creatorList.isNullOrEmpty()) {
                comic.data?.creators?.items = listOf(
                    Creator(
                        "No creator registered",
                        "",
                        "No role registered"
                    )
                )
            }
            Resource.Success(comic.data!!)
        }

}