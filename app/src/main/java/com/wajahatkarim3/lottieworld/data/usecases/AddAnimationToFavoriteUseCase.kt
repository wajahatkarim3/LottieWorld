package com.wajahatkarim3.lottieworld.data.usecases

import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.data.repository.AnimationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class AddAnimationToFavoriteUseCase(
    private val animationsRepository: AnimationsRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): FlowUseCase<AnimationModel, Unit>(dispatcher) {

    override fun execute(animation: AnimationModel) = flow {
        animationsRepository.addAnimationToFavorite(animation)
        emit(Unit)
    }
}