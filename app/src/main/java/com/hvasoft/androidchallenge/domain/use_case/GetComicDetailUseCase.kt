package com.hvasoft.androidchallenge.domain.use_case

import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.data.models.Creator
import com.hvasoft.androidchallenge.domain.repository.ComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetComicDetailUseCase(
    private val repository: ComicRepository,
) {

    operator fun invoke(comicId: String): Flow<Comic> = flow {
        val comicDetail = repository.getComicDetail(comicId).first()

        val comicResume = comicDetail.description
        if (comicResume.isNullOrEmpty()) {
            comicDetail.description = "This comic has no description."
        }

        val creatorList = comicDetail.creators.items
        if (creatorList.isNotEmpty()) {
            val creatorUri = comicDetail.creators.items.first().resourceURI
            val creatorId = creatorUri.substringAfterLast("/")
            val creatorThumbnail = repository.getThumbnailCreator(creatorId)
                .body()?.creatorsData?.results?.firstOrNull()?.thumbnail
            if (creatorThumbnail != null) {
                comicDetail.creators.items.first().thumbnail = creatorThumbnail
            }
        } else {
            comicDetail.creators.items = listOf(
                Creator(
                    "No creator registered",
                    "",
                    "No role registered",
                    null
                )
            )
        }

        emit(comicDetail)
    }

}
