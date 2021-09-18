package com.wajahatkarim3.lottieworld.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wajahatkarim3.lottieworld.data.model.BlogModel
import com.wajahatkarim3.lottieworld.data.onEmpty
import com.wajahatkarim3.lottieworld.data.onError
import com.wajahatkarim3.lottieworld.data.onLoading
import com.wajahatkarim3.lottieworld.data.onSuccess
import com.wajahatkarim3.lottieworld.data.usecases.FetchAllBlogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val loadAllBlogsUseCase: FetchAllBlogsUseCase
): ViewModel() {

    private val _uiState = MutableLiveData<NewsUIState>()
    val uiState: LiveData<NewsUIState> = _uiState

    private val _blogsList = MutableLiveData<List<BlogModel>>()
    val blogsList: LiveData<List<BlogModel>> = _blogsList

    fun loadNews() {
        viewModelScope.launch {
            loadAllBlogsUseCase.invoke(Unit).collect { dataResource ->
                dataResource.onSuccess {
                    _blogsList.value = this.data!!
                    _uiState.value = ContentState
                }.onLoading {
                    _uiState.value = Loading
                }.onEmpty {
                    _uiState.value = EmptyState
                }.onError {
                    _uiState.value = Error(this.exception.message!!)
                }
            }
        }
    }
}