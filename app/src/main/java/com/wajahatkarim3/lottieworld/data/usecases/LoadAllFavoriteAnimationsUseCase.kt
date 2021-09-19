package com.wajahatkarim3.lottieworld.data.usecases

import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.data.repository.AnimationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class LoadAllFavoriteAnimationsUseCase(
    private val animationsRepository: AnimationsRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): FlowUseCase<Unit, DataResource<List<AnimationModel>>>(dispatcher) {

    override fun execute(parameters: Unit): Flow<DataResource<List<AnimationModel>>> = animationsRepository.loadAllFavoriteAnimations()
}