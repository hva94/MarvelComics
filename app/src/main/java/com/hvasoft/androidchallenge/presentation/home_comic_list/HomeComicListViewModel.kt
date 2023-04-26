package com.hvasoft.androidchallenge.presentation.home_comic_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.domain.use_case.ComicUseCases
import com.hvasoft.androidchallenge.domain.utils.Resource
import com.hvasoft.androidchallenge.domain.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeComicListViewModel @Inject constructor(
    private val useCases: ComicUseCases,
) : ViewModel() {

    private val _stateFlowComicList =
        MutableStateFlow(Resource<List<Comic>>(Status.SUCCESS, emptyList(), null))
    val stateFlowComicList = _stateFlowComicList.asStateFlow()

    init {
        getInitialComics()
    }

    private fun getInitialComics() {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getComics().collect {
                _stateFlowComicList.value = it
            }
        }
    }

    fun getComicsByStartingTitle(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getComicsByStartingTitle(title).collect {
                _stateFlowComicList.value = it
            }
        }
    }
}