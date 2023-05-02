package com.hvasoft.androidchallenge.presentation.favorite_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hvasoft.androidchallenge.data.model.Comic
import com.hvasoft.androidchallenge.domain.use_case.ComicsUseCases
import com.hvasoft.androidchallenge.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteComicListViewModel@Inject constructor(
    private val useCases: ComicsUseCases
) : ViewModel() {

    private val _stateFlowComicList = MutableStateFlow<Resource<List<Comic>>>(Resource.Loading())
    val stateFlowComicList = _stateFlowComicList.asStateFlow()

    init {
        getFavoriteComics()
    }

    private fun getFavoriteComics() {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getFavoriteComics().collect {
                _stateFlowComicList.value = it
            }
        }
    }

}