package com.wajahatkarim3.lottieworld.ui.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.wajahatkarim3.lottieworld.TestDataUtils
import com.wajahatkarim3.lottieworld.TestingCoroutineRule
import com.wajahatkarim3.lottieworld.base.BaseUIState
import com.wajahatkarim3.lottieworld.base.ContentState
import com.wajahatkarim3.lottieworld.data.DataResource
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.data.model.AnimatorModel
import com.wajahatkarim3.lottieworld.data.usecases.LoadAllFavoriteAnimationsUseCase
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
class FavoritesViewModelTest {

    // Subject under Test
    private lateinit var viewModel: FavoritesViewModel

    @MockK
    private lateinit var loadAllFavoriteAnimationsUseCase: LoadAllFavoriteAnimationsUseCase

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
    fun `test when FavoritesViewModel is initialized, favorite animations are loaded from database`() = runBlocking {
        // Given
        val givenAnimations = TestDataUtils.createAnimationsList(3)
        val uiObserver = mockk<Observer<BaseUIState>>(relaxed = true)
        val animsListObserver = mockk<Observer<List<AnimationModel>>>(relaxed = true)

        // When
        coEvery { loadAllFavoriteAnimationsUseCase.invoke(Unit) }
            .returns(flowOf(DataResource.Success(givenAnimations)))

        // Invoke
        viewModel = FavoritesViewModel(loadAllFavoriteAnimationsUseCase)
        viewModel.uiState.observeForever(uiObserver)
        viewModel.animsList.observeForever(animsListObserver)

        // Then
        coVerify(exactly = 1) { loadAllFavoriteAnimationsUseCase.invoke(Unit) }
        verify { uiObserver.onChanged(match { it == ContentState }) }
        verify { animsListObserver.onChanged(match { it.size == givenAnimations.size }) }
    }
}