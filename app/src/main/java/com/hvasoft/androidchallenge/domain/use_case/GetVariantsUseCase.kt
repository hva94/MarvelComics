package com.hvasoft.androidchallenge.domain.use_case

import com.hvasoft.androidchallenge.data.model.Comic
import com.hvasoft.androidchallenge.data.model.Variant
import com.hvasoft.androidchallenge.domain.ComicRepository
import com.hvasoft.androidchallenge.domain.utils.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class GetVariantsUseCase(
    private val repository: ComicRepository,
) {

    @OptIn(FlowPreview::class)
    operator fun invoke(variants: List<Variant>): Flow<Resource<List<Comic>>> {
        return flow {
            if (variants.isEmpty()) {
                val emptyListResource = Resource.Success(emptyList<Comic>())
                emit(emptyListResource)
            } else {
                val comicList = mutableListOf<Resource<Comic>>()
                getVariantsFlow(variants)
                    .flatMapMerge { variant ->
                        getDataToVariantFlow(variant)
                    }
                    .collect { comic ->
                        comicList.add(Resource.Success(comic))
                        emit(Resource.Success(comicList.map { it.data!! }))
                    }
            }
        }
    }

    private fun getVariantsFlow(variants: List<Variant>) = flow {
        variants.forEach { variant ->
            emit(variant)
        }
    }

    private fun getDataToVariantFlow(variant: Variant): Flow<Comic> =
        flow {
            val comicId = variant.resourceURI.substringAfterLast("/")
            repository.getComicById(comicId)
                .collect { comic ->
                    comic.data?.let { emit(it) }
                }
        }

}