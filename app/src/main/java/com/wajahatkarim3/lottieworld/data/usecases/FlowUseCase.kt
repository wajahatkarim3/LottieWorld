package com.wajahatkarim3.lottieworld.data.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Result<R>].
 */
abstract class FlowUseCase<in Param, Result>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: Param): Flow<Result> = execute(parameters)
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: Param): Flow<Result>
}