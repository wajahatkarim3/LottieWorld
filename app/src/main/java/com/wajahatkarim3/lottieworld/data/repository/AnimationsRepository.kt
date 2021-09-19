package com.wajahatkarim3.lottieworld.data.repository

import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.callApi
import com.wajahatkarim3.lottieworld.data.local.AnimationsDao
import com.wajahatkarim3.lottieworld.data.model.*
import com.wajahatkarim3.lottieworld.data.remote.LottieFilesApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface AnimationsRepository {
    fun fetchFeaturedAnimators(): Flow<DataResource<List<AnimatorModel>>>
    fun fetchAnimations(animationType: AnimationType): Flow<DataResource<List<AnimationModel>>>
    suspend fun addAnimationToFavorite(animationModel: AnimationModel)
    fun loadAllFavoriteAnimations(): Flow<DataResource<List<AnimationModel>>>
}

class DefaultAnimationsRepository @Inject constructor(
    private val apiService: LottieFilesApiService,
    private val animationsDao: AnimationsDao,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
): AnimationsRepository {

    override fun fetchFeaturedAnimators(): Flow<DataResource<List<AnimatorModel>>> = flow {
        emit(DataResource.Loading)
        val result = callApi {
            val animResponse = apiService.loadFeaturedAnimators()
            animResponse.animatorsData.animators.results
        }
        emit(result)
    }.flowOn(dispatcher)

    override fun fetchAnimations(animationType: AnimationType): Flow<DataResource<List<AnimationModel>>> = flow {
        emit(DataResource.Loading)
        val result = callApi {
            when(animationType) {
                ANIMATION_TYPE_FEATURED -> {
                    val anims = apiService.loadFeaturedAnimations()
                    anims.animationsData.animations.results
                }
                ANIMATION_TYPE_POPULAR -> apiService.loadPopularAnimations().animationsData.animations.results
                ANIMATION_TYPE_RECENT -> apiService.loadRecentAnimations().animationsData.animations.results
                ANIMATION_TYPE_NONE -> apiService.loadFeaturedAnimations().animationsData.animations.results
            }
        }
        emit(result)
    }.flowOn(dispatcher)

    override suspend fun addAnimationToFavorite(animationModel: AnimationModel) {
        animationsDao.insert(listOf(animationModel))
    }

    override fun loadAllFavoriteAnimations(): Flow<DataResource<List<AnimationModel>>> = flow {
        emit(DataResource.Loading)
        val result = animationsDao.queryAll()
        if (result.isEmpty())
            emit(DataResource.Empty)
        else emit(DataResource.Success(result))
    }.flowOn(dispatcher)
}