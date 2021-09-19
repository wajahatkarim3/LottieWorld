package com.wajahatkarim3.lottieworld.ui.anim_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wajahatkarim3.lottieworld.base.BaseUIState
import com.wajahatkarim3.lottieworld.base.ContentState
import com.wajahatkarim3.lottieworld.base.ErrorState
import com.wajahatkarim3.lottieworld.base.LoadingState
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.data.usecases.AddAnimationToFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimationDetailsViewModel @Inject constructor(
    private val addAnimationToFavoriteUseCase: AddAnimationToFavoriteUseCase
): ViewModel() {

    private val _uiState = MutableLiveData<BaseUIState>()
    val uiState: LiveData<BaseUIState> = _uiState

    private val _animationModel = MutableLiveData<AnimationModel?>()
    val animationModel: LiveData<AnimationModel?> = _animationModel

    private val _lottieUrl = MutableLiveData<String>()
    val lottieUrl: LiveData<String> = _lottieUrl

    fun initAnimation(animation: AnimationModel) {
        _animationModel.value = animation

        initAnimationFromUrl(animation.lottieUrl)
    }

    fun initAnimationFromUrl(url: String) {
        _uiState.value = LoadingState
        _lottieUrl.value = url
    }

    fun showContent() {
        _uiState.value = ContentState
    }

    fun showError(message: String) {
        _uiState.value = ErrorState(message)
    }

    fun likeAnimation() {
        _animationModel.value?.let {
            viewModelScope.launch {
                addAnimationToFavoriteUseCase.invoke(it).collect { }
            }
        }
    }
}