package com.dpfht.tmdbcleanmvi.feature_splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SplashViewModel

    @Mock
    private lateinit var navigationService: NavigationService

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SplashViewModel(navigationService)
    }

    @Test
    fun `ensuring has finished delaying when start() method in SplashViewModel is called`() = runTest {
        viewModel.intents.send(SplashIntent.Init)

        advanceUntilIdle()

        verify(navigationService).navigateToGender()
    }
}
