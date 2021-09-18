package com.wajahatkarim3.lottieworld.data.usecases

import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.model.BlogModel
import com.wajahatkarim3.lottieworld.data.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class FetchAllBlogsUseCase(
    private val newsRepository: NewsRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): FlowUseCase<Unit, DataResource<List<BlogModel>>>(dispatcher) {

    @ExperimentalCoroutinesApi
    override fun execute(parameters: Unit): Flow<DataResource<List<BlogModel>>> = newsRepository.fetchAllBlogs()
}