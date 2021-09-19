package com.wajahatkarim3.lottieworld.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wajahatkarim3.lottieworld.TestDataUtils
import com.wajahatkarim3.lottieworld.TestingCoroutineRule
import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.remote.LottieFilesApiService
import com.wajahatkarim3.lottieworld.data.remote.responses.LoadBlogsResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DefaultNewsRepositoryTest {

    // Subject under Test
    private lateinit var repository: DefaultNewsRepository

    @MockK
    private lateinit var apiService: LottieFilesApiService

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestingCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test fetchAllBlogs() gives list of Blogs`() = runBlocking {
        // Given
        repository = DefaultNewsRepository(apiService, coroutinesRule.testDispatcher)
        val blogsResponse = TestDataUtils.createBlogsApiResponse(3)
        val givenBlogsList = blogsResponse.blogs.results

        // When
        coEvery { apiService.loadBlogs() }
            .returns(LoadBlogsResponse(blogsResponse))

        // Invoke
        val apiResponseFlow = repository.fetchAllBlogs()

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val blogsDataResource = apiResponseFlow.last()
        MatcherAssert.assertThat(blogsDataResource, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(blogsDataResource, CoreMatchers.instanceOf(DataResource.Success::class.java))

        val blogsList = (blogsDataResource as DataResource.Success).data
        MatcherAssert.assertThat(blogsList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(blogsList.size, CoreMatchers.`is`(givenBlogsList.size))

        coVerify(exactly = 1) { apiService.loadBlogs() }
        confirmVerified(apiService)
    }
}