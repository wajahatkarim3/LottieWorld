package com.wajahatkarim3.lottieworld.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wajahatkarim3.lottieworld.base.*
import com.wajahatkarim3.lottieworld.data.model.*
import com.wajahatkarim3.lottieworld.data.onEmpty
import com.wajahatkarim3.lottieworld.data.onError
import com.wajahatkarim3.lottieworld.data.onLoading
import com.wajahatkarim3.lottieworld.data.onSuccess
import com.wajahatkarim3.lottieworld.data.usecases.AddAnimationToFavoriteUseCase
import com.wajahatkarim3.lottieworld.data.usecases.FetchAnimationsUseCase
import com.wajahatkarim3.lottieworld.data.usecases.FetchFeaturedAnimatorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val featuredAnimatorsUseCase: FetchFeaturedAnimatorsUseCase,
    private val animationsUseCase: FetchAnimationsUseCase,
    private val addAnimationToFavoriteUseCase: AddAnimationToFavoriteUseCase
): ViewModel() {

    private val _uiAnimatorsState = MutableLiveData<BaseUIState>()
    val uiAnimatorsState: LiveData<BaseUIState> = _uiAnimatorsState

    private val _uiFeaturedState = MutableLiveData<BaseUIState>()
    val uiFeaturedState: LiveData<BaseUIState> = _uiFeaturedState

    private val _uiRecentState = MutableLiveData<BaseUIState>()
    val uiRecentState: LiveData<BaseUIState> = _uiRecentState

    private val _uiPopularState = MutableLiveData<BaseUIState>()
    val uiPopularState: LiveData<BaseUIState> = _uiPopularState

    private val _animatorsList = MutableLiveData<List<AnimatorModel>>()
    val animatorsList: LiveData<List<AnimatorModel>> = _animatorsList

    private val _featuredAnimsList = MutableLiveData<List<AnimationModel>>()
    val featuredAnimsList: LiveData<List<AnimationModel>> = _featuredAnimsList

    private val _recentAnimsList = MutableLiveData<List<AnimationModel>>()
    val recentAnimsList: LiveData<List<AnimationModel>> = _recentAnimsList

    private val _popularAnimsList = MutableLiveData<List<AnimationModel>>()
    val popularAnimsList: LiveData<List<AnimationModel>> = _popularAnimsList

    init {
        loadFeaturedAnimators()
        loadFeaturedAnimations()
        loadPopularAnimations()
        loadRecentAnimations()
    }

    @OptIn(InternalCoroutinesApi::class)
    fun loadFeaturedAnimators() {
        viewModelScope.launch {
            featuredAnimatorsUseCase.invoke(Unit).collect { dataResource ->
                dataResource.onSuccess {
                    _animatorsList.value = this.data!!
                    _uiAnimatorsState.value = ContentState
                }.onLoading {
                    _uiAnimatorsState.value = LoadingState
                }.onEmpty {
                    _uiAnimatorsState.value = EmptyState
                }.onError {
                    _uiAnimatorsState.value = ErrorState(this.exception.message!!)
                }
            }
        }
    }

    @OptIn(InternalCoroutinesApi::class)
    fun loadFeaturedAnimations() {
        viewModelScope.launch {
            animationsUseCase.invoke(ANIMATION_TYPE_FEATURED).collect { dataResource ->
                dataResource.onSuccess {
                    _featuredAnimsList.value = this.data!!
                    _uiFeaturedState.value = ContentState
                }.onLoading {
                    _uiFeaturedState.value = LoadingState
                }.onEmpty {
                    _uiFeaturedState.value = EmptyState
                }.onError {
                    _uiFeaturedState.value = ErrorState(this.exception.message!!)
                }
            }
        }
    }

    @OptIn(InternalCoroutinesApi::class)
    fun loadPopularAnimations() {
        viewModelScope.launch {
            animationsUseCase.invoke(ANIMATION_TYPE_POPULAR).collect { dataResource ->
                dataResource.onSuccess {
                    _popularAnimsList.value = this.data!!
                    _uiPopularState.value = ContentState
                }.onLoading {
                    _uiPopularState.value = LoadingState
                }.onEmpty {
                    _uiPopularState.value = EmptyState
                }.onError {
                    _uiPopularState.value = ErrorState(this.exception.message!!)
                }
            }
        }
    }

    @OptIn(InternalCoroutinesApi::class)
    fun loadRecentAnimations() {
        viewModelScope.launch {
            animationsUseCase.invoke(ANIMATION_TYPE_RECENT).collect { dataResource ->
                dataResource.onSuccess {
                    _recentAnimsList.value = this.data!!
                    _uiRecentState.value = ContentState
                }.onLoading {
                    _uiRecentState.value = LoadingState
                }.onEmpty {
                    _uiRecentState.value = EmptyState
                }.onError {
                    _uiRecentState.value = ErrorState(this.exception.message!!)
                }
            }
        }
    }

    fun refresh() {
        loadFeaturedAnimators()
        loadFeaturedAnimations()
        loadPopularAnimations()
        loadRecentAnimations()
    }

    fun addToFavorite(animation: AnimationModel) {
        viewModelScope.launch {
            addAnimationToFavoriteUseCase.invoke(animation).collect {

            }
        }
    }
}