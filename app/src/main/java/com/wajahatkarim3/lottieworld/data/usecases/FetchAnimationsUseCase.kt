package com.wajahatkarim3.lottieworld.data.usecases

import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.data.model.AnimationType
import com.wajahatkarim3.lottieworld.data.repository.AnimationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class FetchAnimationsUseCase(
    private val animationsRepository: AnimationsRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): FlowUseCase<AnimationType, DataResource<List<AnimationModel>>>(dispatcher) {

    @ExperimentalCoroutinesApi
    override fun execute(animType: AnimationType): Flow<DataResource<List<AnimationModel>>> = animationsRepository.fetchAnimations(animType)
}