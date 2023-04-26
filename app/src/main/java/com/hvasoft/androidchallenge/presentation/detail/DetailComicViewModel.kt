package com.hvasoft.androidchallenge.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hvasoft.androidchallenge.core.exceptions.TypeError
import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.data.models.Variant
import com.hvasoft.androidchallenge.domain.use_case.ComicsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailComicViewModel @Inject constructor(
    private val useCases: ComicsUseCases,
) : ViewModel() {

    private val _stateFlowVariantList = MutableStateFlow(listOf<Variant>())
    val stateFlowVariantList = _stateFlowVariantList.asStateFlow()

    private val _displayComic = MutableStateFlow<Comic?>(null)
    val displayComic = _displayComic.asStateFlow()

    private val _isFavoriteStateFlow = MutableStateFlow(false)
    val isFavoriteTextStateFlow = _isFavoriteStateFlow.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _typeError = MutableStateFlow(TypeError.NONE)
    val typeError = _typeError.asStateFlow()

    fun getComicDetail(comicId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                useCases.getComicDetail(comicId).collect { comic ->
                    val variantList = comic.variants
                    useCases.getVariants(variantList).collect { variants ->
                        _stateFlowVariantList.value = variants
                    }
                    _displayComic.value = comic
                    _isFavoriteStateFlow.value = comic.isFavorite
                }
            } catch (e: IOException) {
                _typeError.value = TypeError.NETWORK
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateFavorite() {
        val comic = displayComic.value ?: return
        comic.isFavorite = !comic.isFavorite

        viewModelScope.launch(Dispatchers.IO) {
            try {
                useCases.updateFavorite(comic)
                _isFavoriteStateFlow.value = comic.isFavorite
            } catch (e: IOException) {
                _typeError.value = TypeError.UPDATE
            }
        }
    }
}