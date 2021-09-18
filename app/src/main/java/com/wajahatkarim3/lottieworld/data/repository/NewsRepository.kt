package com.wajahatkarim3.lottieworld.data.repository

import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.callApi
import com.wajahatkarim3.lottieworld.data.model.BlogModel
import com.wajahatkarim3.lottieworld.data.remote.LottieFilesApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface NewsRepository {
    fun fetchAllBlogs(): Flow<DataResource<List<BlogModel>>>
}

class DefaultNewsRepository @Inject constructor(
    private val apiService: LottieFilesApiService,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
): NewsRepository {

    override fun fetchAllBlogs(): Flow<DataResource<List<BlogModel>>> = flow {
        emit(DataResource.Loading)
        val result = callApi {
            val blogsResponse = apiService.loadBlogs()
            blogsResponse.blogsData.blogs.results
        }
        emit(result)
    }.flowOn(dispatcher)
}