package com.hvasoft.androidchallenge.domain.use_case

import com.hvasoft.androidchallenge.data.model.CreatorEntity
import com.hvasoft.androidchallenge.data.model.Creators
import com.hvasoft.androidchallenge.data.model.Thumbnail
import com.hvasoft.androidchallenge.domain.ComicRepository
import com.hvasoft.androidchallenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetFirstCreatorUseCase(
    private val repository: ComicRepository,
) {

    operator fun invoke(creators: Creators): Flow<Resource<CreatorEntity>> {
        val creatorUri = creators.items.first().resourceURI
        val creatorId = creatorUri.substringAfterLast("/")

        return if (creatorId.isEmpty()) {
            flow {
                val emptyCreator = Resource.Success(
                    CreatorEntity(
                        1,
                        "No creator registered",
                        "",
                        Thumbnail("", "")
                    )
                )
                emit(emptyCreator)
            }
        } else {
            val creatorFlow = repository.getCreatorById(creatorId)
            creatorFlow.map { creator ->
                if (creator.data != null) {
                    Resource.Success(creator.data)
                } else {
                    if (creator.error == null) Resource.Loading()
                    else Resource.Error(creator.error)
                }
            }
        }
    }

}