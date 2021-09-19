package com.wajahatkarim3.lottieworld.data.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wajahatkarim3.lottieworld.TestDataUtils
import com.wajahatkarim3.lottieworld.TestingCoroutineRule
import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.model.ANIMATION_TYPE_FEATURED
import com.wajahatkarim3.lottieworld.data.repository.AnimationsRepository
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
class FetchAnimationsUseCaseTest {

    @MockK
    private lateinit var repository: AnimationsRepository

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
    fun `test invoking FetchAnimationsUseCase gives list of Animations`() = runBlocking {
        // Given
        val usecase = FetchAnimationsUseCase(repository, coroutinesRule.testDispatcher)
        val givenAnims = TestDataUtils.createAnimationsList(3)

        // When
        coEvery { repository.fetchAnimations(any()) }
            .returns(flowOf(DataResource.Success(givenAnims)))

        // Invoke
        val animsListFlow = usecase(ANIMATION_TYPE_FEATURED)

        // Then
        MatcherAssert.assertThat(animsListFlow, CoreMatchers.notNullValue())

        val animsListDataResource = animsListFlow.last()
        MatcherAssert.assertThat(animsListDataResource, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(animsListDataResource, CoreMatchers.instanceOf(DataResource.Success::class.java))

        val animsList = (animsListDataResource as DataResource.Success).data
        MatcherAssert.assertThat(animsList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(animsList.size, `is`(givenAnims.size))
    }
}