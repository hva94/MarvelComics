package com.hvasoft.androidchallenge.presentation.detail_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hvasoft.androidchallenge.core.exceptions.ComicsException
import com.hvasoft.androidchallenge.core.exceptions.TypeError
import com.hvasoft.androidchallenge.data.model.Comic
import com.hvasoft.androidchallenge.data.model.CreatorEntity
import com.hvasoft.androidchallenge.data.model.Creators
import com.hvasoft.androidchallenge.data.model.Variant
import com.hvasoft.androidchallenge.domain.use_case.ComicsUseCases
import com.hvasoft.androidchallenge.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailComicViewModel @Inject constructor(
    private val useCases: ComicsUseCases,
) : ViewModel() {

    private val _comicStateFlow = MutableStateFlow<Resource<Comic>>(Resource.Loading())
    val comicStateFlow = _comicStateFlow.asStateFlow()

    private val _creatorStateFlow = MutableStateFlow<Resource<CreatorEntity>>(Resource.Loading())
    val creatorStateFlow = _creatorStateFlow.asStateFlow()

    private val _variantListStateFlow = MutableStateFlow<Resource<List<Comic>>>(Resource.Loading())
    val variantListStateFlow = _variantListStateFlow.asStateFlow()

    private val _isFavoriteStateFlow = MutableStateFlow(false)
    val isFavoriteTextStateFlow = _isFavoriteStateFlow.asStateFlow()

    private val _typeError = MutableStateFlow(TypeError.NONE)
    val typeError = _typeError.asStateFlow()

    fun getComicDetail(comicId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getComicDetail(comicId).collect { comicResource ->
                _comicStateFlow.value = comicResource
                comicResource.data?.let {
                    _isFavoriteStateFlow.value = it.isFavorite
                    getFirstCreator(it.creators)
                    getVariants(it.variants)
                }
            }
        }
    }

    private fun getFirstCreator(creators: Creators) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getFirstCreator(creators).collect {
                _creatorStateFlow.value = it
            }
        }
    }

    private fun getVariants(variants: List<Variant>) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getVariants(variants).collect {
                _variantListStateFlow.value = it
            }
        }
    }

    fun updateFavorite() {
        val comic: Comic = _comicStateFlow.value.data ?: return
        comic.isFavorite = !comic.isFavorite

        viewModelScope.launch(Dispatchers.IO) {
            try {
                useCases.updateFavorite(comic)
                _isFavoriteStateFlow.value = comic.isFavorite
            } catch (e: ComicsException) {
                _typeError.value = e.typeError
            }
        }
    }
}