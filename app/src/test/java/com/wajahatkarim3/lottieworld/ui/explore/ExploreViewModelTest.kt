package com.wajahatkarim3.lottieworld.ui.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.wajahatkarim3.lottieworld.TestDataUtils
import com.wajahatkarim3.lottieworld.TestingCoroutineRule
import com.wajahatkarim3.lottieworld.base.BaseUIState
import com.wajahatkarim3.lottieworld.base.ContentState
import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.model.ANIMATION_TYPE_FEATURED
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.data.model.AnimatorModel
import com.wajahatkarim3.lottieworld.data.model.BlogModel
import com.wajahatkarim3.lottieworld.data.usecases.AddAnimationToFavoriteUseCase
import com.wajahatkarim3.lottieworld.data.usecases.FetchAnimationsUseCase
import com.wajahatkarim3.lottieworld.data.usecases.FetchFeaturedAnimatorsUseCase
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
class ExploreViewModelTest {

    // Subject under Test
    private lateinit var viewModel: ExploreViewModel

    @MockK
    private lateinit var fetchFeaturedAnimatorsUseCase: FetchFeaturedAnimatorsUseCase

    @MockK
    private lateinit var fetchAnimationsUseCase: FetchAnimationsUseCase

    @MockK
    private lateinit var addAnimationToFavoriteUseCase: AddAnimationToFavoriteUseCase

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
    fun `test when ExploreViewModel is initialized, animators are loaded`() = runBlocking {
        // Given
        val givenAnimators = TestDataUtils.createAnimatorsList(3)
        val uiObserver = mockk<Observer<BaseUIState>>(relaxed = true)
        val animatorsListObserver = mockk<Observer<List<AnimatorModel>>>(relaxed = true)

        // When
        coEvery { fetchFeaturedAnimatorsUseCase.invoke(Unit) }
            .returns(flowOf(DataResource.Success(givenAnimators)))
        coEvery { fetchAnimationsUseCase.invoke(any()) }
            .returns(flowOf(DataResource.Success(TestDataUtils.createAnimationsList(3))))

        // Invoke
        viewModel = ExploreViewModel(fetchFeaturedAnimatorsUseCase, fetchAnimationsUseCase, addAnimationToFavoriteUseCase)
        viewModel.uiAnimatorsState.observeForever(uiObserver)
        viewModel.animatorsList.observeForever(animatorsListObserver)

        // Then
        coVerify(exactly = 1) { fetchFeaturedAnimatorsUseCase.invoke(Unit) }
        verify { uiObserver.onChanged(match { it == ContentState }) }
        verify { animatorsListObserver.onChanged(match { it.size == givenAnimators.size }) }
    }

    @Test
    fun `test when ExploreViewModel is initialized, featured animations are loaded`() = runBlocking {
        // Given
        val givenAnims = TestDataUtils.createAnimationsList(3)
        val uiObserver = mockk<Observer<BaseUIState>>(relaxed = true)
        val animsListObserver = mockk<Observer<List<AnimationModel>>>(relaxed = true)

        // When
        coEvery { fetchFeaturedAnimatorsUseCase.invoke(Unit) }
            .returns(flowOf(DataResource.Success(TestDataUtils.createAnimatorsList(3))))
        coEvery { fetchAnimationsUseCase.invoke(any()) }
            .returns(flowOf(DataResource.Success(givenAnims)))

        // Invoke
        viewModel = ExploreViewModel(fetchFeaturedAnimatorsUseCase, fetchAnimationsUseCase, addAnimationToFavoriteUseCase)
        viewModel.uiAnimatorsState.observeForever(uiObserver)
        viewModel.featuredAnimsList.observeForever(animsListObserver)

        // Then
        coVerify(atLeast = 1) { fetchAnimationsUseCase.invoke(any()) }
        verify { uiObserver.onChanged(match { it == ContentState }) }
        verify { animsListObserver.onChanged(match { it.size == givenAnims.size }) }
    }
}