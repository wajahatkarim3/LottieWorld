package com.wajahatkarim3.lottieworld.data.repository

import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.model.AnimatorModel
import com.wajahatkarim3.lottieworld.utils.StringUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface UsersRepository {
    fun loginUser(): Flow<DataResource<AnimatorModel>>
    fun logoutUser(): Flow<DataResource<Boolean>>
    fun loadLoggedinUser(): Flow<DataResource<AnimatorModel?>>
}

class DefaultUsersRepository(
    val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val preferencesRepository: PreferencesRepository,
    private val stringUtils: StringUtils
): UsersRepository {

    override fun loginUser(): Flow<DataResource<AnimatorModel>> = flow {
        emit(DataResource.Loading)
        kotlinx.coroutines.delay(2000)
        emit(DataResource.Success(generateFakeUser()))
        preferencesRepository.setUserLogin(true)
    }.flowOn(dispatcher)

    override fun logoutUser(): Flow<DataResource<Boolean>> = flow {
        emit(DataResource.Loading)
        kotlinx.coroutines.delay(2000)
        emit(DataResource.Success(true))
        preferencesRepository.setUserLogin(false)
    }.flowOn(dispatcher)

    override fun loadLoggedinUser(): Flow<DataResource<AnimatorModel?>> = flow {
        emit(DataResource.Loading)
        if (preferencesRepository.isUserLoggedIn()) {
            // Load from either local or network
            kotlinx.coroutines.delay(2000)
            emit(DataResource.Success(generateFakeUser()))
        } else {
            // User logged out somehow. Either app cleared or server expired token
            preferencesRepository.setUserLogin(false)
            emit(DataResource.Success(null))
        }
    }.flowOn(dispatcher)

    private fun generateFakeUser() = AnimatorModel(
        name = "Wajahat Karim",
        avatarUrl = "https://www.gravatar.com/avatar/cb3e26de5b49f50cc74a98a7729b3f93?s=4000",
        username = "@wajahatkarim3"
    )

}