package com.wajahatkarim3.lottieworld.data.usecases

import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.model.AnimatorModel
import com.wajahatkarim3.lottieworld.data.repository.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class LoadLoggedInUserUserCase(
    private val usersRepository: UsersRepository,
    private val dispatcher: CoroutineDispatcher
): FlowUseCase<Unit, DataResource<AnimatorModel?>>(dispatcher) {

    override fun execute(parameters: Unit): Flow<DataResource<AnimatorModel?>> = usersRepository.loadLoggedinUser()
}