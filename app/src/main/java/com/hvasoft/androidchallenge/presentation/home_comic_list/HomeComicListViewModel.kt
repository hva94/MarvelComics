package com.hvasoft.androidchallenge.presentation.home_comic_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.domain.use_case.ComicsUseCases
import com.hvasoft.androidchallenge.domain.utils.Resource
import com.hvasoft.androidchallenge.domain.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeComicListViewModel @Inject constructor(
    private val useCases: ComicsUseCases
) : ViewModel() {

    private val _stateFlowComicList =
        MutableStateFlow(Resource<List<Comic>>(Status.LOADING, emptyList(), null))
    val stateFlowComicList = _stateFlowComicList.asStateFlow()

    init {
        getComics()
    }

    fun getComics() {
        _stateFlowComicList.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                useCases.getComics().collect {
                    _stateFlowComicList.value = it
                }
            } catch (e: IOException) {
                _stateFlowComicList.value = Resource.error(e.message.toString(), null)
            }
        }
    }

    fun getComicsByStartingTitle(title: String) {
        _stateFlowComicList.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                useCases.getComicsByStartingTitle(title).onCompletion {
                    if (it == null) {
                        _stateFlowComicList.value = Resource.error("Failed to fetch data", null)
                    }
                }.collect {
                    _stateFlowComicList.value = it
                }
            } catch (e: IOException) {
                _stateFlowComicList.value = Resource.error(e.message.toString(), null)
            }
        }
    }
}