package com.wajahatkarim3.lottieworld.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wajahatkarim3.lottieworld.base.*
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.data.onEmpty
import com.wajahatkarim3.lottieworld.data.onError
import com.wajahatkarim3.lottieworld.data.onLoading
import com.wajahatkarim3.lottieworld.data.onSuccess
import com.wajahatkarim3.lottieworld.data.usecases.LoadAllFavoriteAnimationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val loadAllFavoriteAnimationsUseCase: LoadAllFavoriteAnimationsUseCase
): ViewModel() {

    private val _uiState = MutableLiveData<BaseUIState>()
    val uiState: LiveData<BaseUIState> = _uiState

    private val _animsList = MutableLiveData<List<AnimationModel>>()
    val animsList: LiveData<List<AnimationModel>> = _animsList

    init {
        loadAllFavoriteAnims()
    }

    fun loadAllFavoriteAnims() {
        viewModelScope.launch {
            loadAllFavoriteAnimationsUseCase.invoke(Unit).collect { dataResource ->
                dataResource.onSuccess {
                    _animsList.value = this.data!!
                    _uiState.value = ContentState
                }.onLoading {
                    _uiState.value = LoadingState
                }.onEmpty {
                    _uiState.value = EmptyState
                }.onError {
                    _uiState.value = ErrorState(this.exception.message!!)
                }
            }
        }
    }
}