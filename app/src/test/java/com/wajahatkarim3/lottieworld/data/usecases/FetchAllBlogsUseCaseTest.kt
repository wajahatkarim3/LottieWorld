package com.wajahatkarim3.lottieworld.data.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wajahatkarim3.lottieworld.TestDataUtils
import com.wajahatkarim3.lottieworld.TestingCoroutineRule
import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.repository.NewsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchAllBlogsUseCaseTest {

    @MockK
    private lateinit var repository: NewsRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestingCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test invoking FetchAllBlogsUseCase gives list of Blogs`() = runBlocking {
        // Given
        val usecase = FetchAllBlogsUseCase(repository, coroutinesRule.testDispatcher)
        val givenBlogs = TestDataUtils.createBlogItemsList(3)

        // When
        coEvery { repository.fetchAllBlogs() }
            .returns(flowOf(DataResource.Success(givenBlogs)))

        // Invoke
        val blogsListFlow = usecase(Unit)

        // Then
        MatcherAssert.assertThat(blogsListFlow, CoreMatchers.notNullValue())

        val blogsListDataResource = blogsListFlow.last()
        MatcherAssert.assertThat(blogsListDataResource, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(blogsListDataResource, CoreMatchers.instanceOf(DataResource.Success::class.java))

        val blogsList = (blogsListDataResource as DataResource.Success).data
        MatcherAssert.assertThat(blogsList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(blogsList.size, `is`(givenBlogs.size))
    }
}