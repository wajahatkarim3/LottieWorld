package com.wajahatkarim3.lottieworld.data.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wajahatkarim3.lottieworld.TestDataUtils
import com.wajahatkarim3.lottieworld.TestingCoroutineRule
import com.wajahatkarim3.lottieworld.data.repository.AnimationsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AddAnimationToFavoriteUseCaseTest {

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
    fun `test invoking AddAnimationToFavoriteUseCase adds animation in database`() = runBlocking {
        // Given
        val useCase = AddAnimationToFavoriteUseCase(repository, coroutinesRule.testDispatcher)
        val givenAnimation = TestDataUtils.createAnimationsList(1)[0]

        // When
        coEvery { repository.addAnimationToFavorite(any()) }
            .returns(Unit)

        // Invoke
        val response = useCase(givenAnimation).last()

        // Then
        coVerify(exactly = 1) { repository.addAnimationToFavorite(any()) }
        confirmVerified(repository)
    }
}