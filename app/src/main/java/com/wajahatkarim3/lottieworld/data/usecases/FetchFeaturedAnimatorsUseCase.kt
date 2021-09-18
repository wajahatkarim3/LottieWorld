package com.wajahatkarim3.lottieworld.data.usecases

import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.model.AnimatorModel
import com.wajahatkarim3.lottieworld.data.repository.AnimationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class FetchFeaturedAnimatorsUseCase(
    private val animationsRepository: AnimationsRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): FlowUseCase<Unit, DataResource<List<AnimatorModel>>>(dispatcher) {

    @ExperimentalCoroutinesApi
    override fun execute(parameters: Unit): Flow<DataResource<List<AnimatorModel>>> = animationsRepository.fetchFeaturedAnimators()
}