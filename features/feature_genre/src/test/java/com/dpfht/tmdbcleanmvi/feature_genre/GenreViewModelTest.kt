package com.dpfht.tmdbcleanmvi.feature_genre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbcleanmvi.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvi.domain.entity.GenreEntity
import com.dpfht.tmdbcleanmvi.domain.entity.Result
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbcleanmvi.feature_genre.adapter.GenreAdapter
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GenreViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: GenreViewModel

  @Mock
  private lateinit var adapter: GenreAdapter

  @Mock
  private lateinit var getMovieGenreUseCase: GetMovieGenreUseCase

  @Mock
  private lateinit var navigationService: NavigationService

  private val genre1 = GenreEntity(1, "Cartoon")
  private val genre2 = GenreEntity(2, "Drama")
  private val genre3 = GenreEntity(3, "Horror")

  private val genres = listOf(genre1, genre2, genre3)

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    viewModel = GenreViewModel(getMovieGenreUseCase, arrayListOf(), adapter, navigationService)
  }

  @Test
  fun `fetch movie genre successfully`() = runTest {
    val getMovieGenreResult = GenreDomain(genres)
    val result = Result.Success(getMovieGenreResult)
    whenever(getMovieGenreUseCase.invoke()).thenReturn(result)

    viewModel.intents.send(GenreIntent.FetchGenre)

    verify(adapter, times(genres.size)).notifyItemInserted(anyInt())
    assertTrue(!viewModel.state.value.isLoading)

    viewModel.intents.send(GenreIntent.NavigateToNextScreen(2))
    verify(navigationService).navigateToMoviesByGender(anyInt(), anyString())
  }

  @Test
  fun `failed fetch movie genre`() = runTest {
    val msg = "error fetch genre"
    val result = Result.Error(msg)

    whenever(getMovieGenreUseCase.invoke()).thenReturn(result)

    viewModel.intents.send(GenreIntent.FetchGenre)

    verify(navigationService).navigateToErrorMessage(msg)
    assertTrue(!viewModel.state.value.isLoading)
  }

  @Test
  fun `navigate to next screen`() = runTest {
    val getMovieGenreResult = GenreDomain(genres)
    val result = Result.Success(getMovieGenreResult)
    whenever(getMovieGenreUseCase.invoke()).thenReturn(result)

    viewModel.intents.send(GenreIntent.FetchGenre)

    viewModel.intents.send(GenreIntent.NavigateToNextScreen(2))
    verify(navigationService).navigateToMoviesByGender(anyInt(), anyString())
  }
}
