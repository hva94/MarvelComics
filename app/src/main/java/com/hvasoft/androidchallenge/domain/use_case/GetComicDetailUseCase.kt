package com.hvasoft.androidchallenge.domain.use_case

import com.hvasoft.androidchallenge.core.exceptions.ComicsException
import com.hvasoft.androidchallenge.core.exceptions.TypeError
import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.data.models.Creator
import com.hvasoft.androidchallenge.data.models.Variant
import com.hvasoft.androidchallenge.domain.ComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetComicDetailUseCase(
    private val repository: ComicRepository,
) {

    operator fun invoke(comicId: String): Flow<Comic> = flow {
        val comicDetail = repository.getComicById(comicId).first()

        if (comicDetail.description.isNullOrEmpty()) {
            comicDetail.description = "This comic has no description."
        }

        val creatorList = comicDetail.creators.items
        if (creatorList.isNotEmpty()) {
            val creatorUri = comicDetail.creators.items.first().resourceURI
            val creatorId = creatorUri.substringAfterLast("/")
            try {
                val creatorThumbnail = repository.getThumbnailCreatorById(creatorId)
                    .body()?.creatorsData?.results?.firstOrNull()?.thumbnail
                if (creatorThumbnail != null) {
                    comicDetail.creators.items.first().thumbnail = creatorThumbnail
                }
            } catch (e: ComicsException) {
                throw ComicsException(TypeError.NETWORK)
            } catch (e: IOException) {
                throw ComicsException(TypeError.NETWORK)
            }
        } else {
            comicDetail.creators.items = listOf(
                Creator(
                    1,
                    "No creator registered",
                    "",
                    "No role registered",
                    null
                )
            )
        }

        val variantList: List<Variant> = comicDetail.variants
        if (variantList.isNotEmpty()) {
            variantList.forEach { variant ->
                val resourceUri = variant.resourceURI
                val comicApiId = resourceUri.split("/").last()
                val variantResponse = repository.fetchComicById(comicApiId)
                variant.id = variantResponse.body()?.comicsData?.results?.first()?.id.toString()
                val variantThumbnail =
                    variantResponse.body()?.comicsData?.results?.first()?.thumbnail
                variant.thumbnail = variantThumbnail ?: variant.thumbnail
            }
        }
        comicDetail.variants = variantList

        emit(comicDetail)
    }

}
