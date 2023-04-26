package com.hvasoft.androidchallenge.domain.use_case

import com.hvasoft.androidchallenge.data.models.Variant
import com.hvasoft.androidchallenge.domain.repository.ComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetVariantsUseCase(
    private val repository: ComicRepository,
) {

    operator fun invoke(variants: List<Variant>): Flow<List<Variant>> {
        return flow {
            variants.forEach { variant ->
                val resourceUri = variant.resourceURI
                val comicId = resourceUri.split("/").last()

                val variantResponse = repository.getVariant(comicId)
                variant.id = variantResponse.body()?.comicsData?.results?.first()?.id.toString()
                val variantThumbnail = variantResponse.body()?.comicsData?.results?.first()?.thumbnail
                variant.thumbnail = variantThumbnail ?: variant.thumbnail
            }
            emit(variants)
        }
    }

}
