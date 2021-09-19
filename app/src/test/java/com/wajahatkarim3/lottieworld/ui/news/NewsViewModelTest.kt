package com.wajahatkarim3.lottieworld.ui.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.wajahatkarim3.lottieworld.TestDataUtils
import com.wajahatkarim3.lottieworld.TestingCoroutineRule
import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.data.model.BlogModel
import com.wajahatkarim3.lottieworld.data.usecases.FetchAllBlogsUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsViewModelTest {

    // Subject under Test
    private lateinit var viewModel: NewsViewModel

    @MockK
    private lateinit var fetchAllBlogsUseCase: FetchAllBlogsUseCase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestingCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test when FavoritesViewModel's loadNews is called, list of blogs are fetch`() = runBlocking {
        // Given
        val givenBlogs = TestDataUtils.createBlogItemsList(3)
        val uiObserver = mockk<Observer<NewsUIState>>(relaxed = true)
        val blogsListObserver = mockk<Observer<List<BlogModel>>>(relaxed = true)

        // When
        coEvery { fetchAllBlogsUseCase.invoke(Unit) }
            .returns(flowOf(DataResource.Success(givenBlogs)))

        // Invoke
        viewModel = NewsViewModel(fetchAllBlogsUseCase)
        viewModel.loadNews()
        viewModel.uiState.observeForever(uiObserver)
        viewModel.blogsList.observeForever(blogsListObserver)

        // Then
        coVerify(exactly = 1) { fetchAllBlogsUseCase.invoke(Unit) }
        verify { uiObserver.onChanged(match { it == ContentState }) }
        verify { blogsListObserver.onChanged(match { it.size == givenBlogs.size }) }
    }
}