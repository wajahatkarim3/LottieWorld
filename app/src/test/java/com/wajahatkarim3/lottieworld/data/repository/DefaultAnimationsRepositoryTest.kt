package com.wajahatkarim3.lottieworld.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wajahatkarim3.lottieworld.TestDataUtils
import com.wajahatkarim3.lottieworld.TestingCoroutineRule
import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.local.AnimationsDao
import com.wajahatkarim3.lottieworld.data.model.ANIMATION_TYPE_FEATURED
import com.wajahatkarim3.lottieworld.data.remote.LottieFilesApiService
import com.wajahatkarim3.lottieworld.data.remote.responses.LoadAnimationsResponse
import com.wajahatkarim3.lottieworld.data.remote.responses.LoadAnimatorsResponse
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

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DefaultAnimationsRepositoryTest {

    // Subject under Test
    private lateinit var repository: DefaultAnimationsRepository

    @MockK
    private lateinit var apiService: LottieFilesApiService

    @MockK
    private lateinit var animationsDao: AnimationsDao

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
    fun `test fetchAnimations() gives list of Animations`() = runBlocking {
        // Given
        repository = DefaultAnimationsRepository(apiService, animationsDao, coroutinesRule.testDispatcher)
        val givenAnimationsDataResponse = TestDataUtils.createAnimationsDataResponse(3)
        val givenAnimsList = givenAnimationsDataResponse.animations.results

        // When
        coEvery { apiService.loadFeaturedAnimations() }
            .returns(LoadAnimationsResponse(givenAnimationsDataResponse))

        // Invoke
        val apiResponseFlow = repository.fetchAnimations(ANIMATION_TYPE_FEATURED)

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val animsDataResource = apiResponseFlow.last()
        MatcherAssert.assertThat(animsDataResource, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(animsDataResource, CoreMatchers.instanceOf(DataResource.Success::class.java))

        val animsList = (animsDataResource as DataResource.Success).data
        MatcherAssert.assertThat(animsList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(animsList.size, CoreMatchers.`is`(givenAnimsList.size))

        coVerify(exactly = 1) { apiService.loadFeaturedAnimations() }
        confirmVerified(apiService)
    }

    @Test
    fun `test fetchFeaturedAnimators() gives list of Animations`() = runBlocking {
        // Given
        repository = DefaultAnimationsRepository(apiService, animationsDao, coroutinesRule.testDispatcher)
        val givenAnimatorsDataResponse = TestDataUtils.createAnimatorsDataResponse(3)
        val givenAnimatorsList = givenAnimatorsDataResponse.animators.results

        // When
        coEvery { apiService.loadFeaturedAnimators() }
            .returns(LoadAnimatorsResponse(givenAnimatorsDataResponse))

        // Invoke
        val apiResponseFlow = repository.fetchFeaturedAnimators()

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val animsDataResource = apiResponseFlow.last()
        MatcherAssert.assertThat(animsDataResource, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(animsDataResource, CoreMatchers.instanceOf(DataResource.Success::class.java))

        val animsList = (animsDataResource as DataResource.Success).data
        MatcherAssert.assertThat(animsList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(animsList.size, CoreMatchers.`is`(givenAnimatorsList.size))

        coVerify(exactly = 1) { apiService.loadFeaturedAnimators() }
        confirmVerified(apiService)
    }

    @Test
    fun `test addAnimationToFavorite() adds Animation in Favorites table`() = runBlocking {
        // Given
        repository = DefaultAnimationsRepository(apiService, animationsDao, coroutinesRule.testDispatcher)

        // When
        coEvery { animationsDao.insert(any()) }
            .returns(Unit)

        // Invoke
        repository.addAnimationToFavorite(TestDataUtils.createAnimationsList(1)[0])

        // Then
        coVerify(exactly = 1) { animationsDao.insert(any()) }
        confirmVerified(animationsDao)
    }

    @Test
    fun `test loadAllFavoriteAnimations() loads Animations from Favorites table`() = runBlocking {
        // Given
        repository = DefaultAnimationsRepository(apiService, animationsDao, coroutinesRule.testDispatcher)
        val givenAnimationsList = TestDataUtils.createAnimationsList(3)

        // When
        coEvery { animationsDao.queryAll() }
            .returns(givenAnimationsList)

        // Invoke
        val animsFlow = repository.loadAllFavoriteAnimations()

        // Then
        MatcherAssert.assertThat(animsFlow, CoreMatchers.notNullValue())

        val animsDataResource = animsFlow.last()
        MatcherAssert.assertThat(animsDataResource, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(animsDataResource, CoreMatchers.instanceOf(DataResource.Success::class.java))

        val animsList = (animsDataResource as DataResource.Success).data
        MatcherAssert.assertThat(animsList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(animsList.size, CoreMatchers.`is`(givenAnimationsList.size))

        // Then
        coVerify(exactly = 1) { animationsDao.queryAll() }
        confirmVerified(animationsDao)
    }
}