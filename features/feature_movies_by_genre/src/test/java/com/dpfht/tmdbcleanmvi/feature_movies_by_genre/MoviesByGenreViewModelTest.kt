package com.dpfht.tmdbcleanmvi.feature_movies_by_genre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbcleanmvi.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvi.domain.entity.MovieEntity
import com.dpfht.tmdbcleanmvi.domain.entity.Result
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.adapter.MoviesByGenreAdapter
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
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MoviesByGenreViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: MoviesByGenreViewModel

  @Mock
  private lateinit var adapter: MoviesByGenreAdapter

  @Mock
  private lateinit var getMovieByGenreUseCase: GetMovieByGenreUseCase

  @Mock
  private lateinit var navigationService: NavigationService

  private val movie1 = MovieEntity(id = 1, title = "title1", overview = "overview1")
  private val movie2 = MovieEntity(id = 2, title = "title2", overview = "overview2")
  private val movie3 = MovieEntity(id = 3, title = "title3", overview = "overview3")

  private val genreId = 1
  private val page = 1

  private val movies = listOf(movie1, movie2, movie3)

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    viewModel = MoviesByGenreViewModel(getMovieByGenreUseCase, arrayListOf(), adapter, navigationService)
  }

  @Test
  fun `fetch movie successfully`() = runTest {
    val getMovieByGenreResult = DiscoverMovieByGenreDomain(page, movies)
    val result = Result.Success(getMovieByGenreResult)

    whenever(getMovieByGenreUseCase.invoke(genreId, page)).thenReturn(result)

    viewModel.setGenreId(genreId)
    viewModel.intents.send(MoviesByGenreIntent.FetchMovie)

    verify(adapter, times(movies.size)).notifyItemInserted(anyInt())
    assertTrue(!viewModel.state.value.isLoading)
  }

  @Test
  fun `fetch next movie successfully`() = runTest {
    val getMovieByGenreResult = DiscoverMovieByGenreDomain(page, movies)
    val result = Result.Success(getMovieByGenreResult)

    whenever(getMovieByGenreUseCase.invoke(genreId, page)).thenReturn(result)

    viewModel.setGenreId(genreId)
    viewModel.intents.send(MoviesByGenreIntent.FetchNextMovie)

    verify(adapter, times(movies.size)).notifyItemInserted(anyInt())
    assertTrue(!viewModel.state.value.isLoading)
  }

  @Test
  fun `fetch next movie movie successfully but empty`() = runTest {
    val movies = arrayListOf<MovieEntity>()
    val getMovieByGenreResult = DiscoverMovieByGenreDomain(page, movies)
    val result = Result.Success(getMovieByGenreResult)

    whenever(getMovieByGenreUseCase.invoke(genreId, page)).thenReturn(result)

    viewModel.setGenreId(genreId)
    viewModel.intents.send(MoviesByGenreIntent.FetchNextMovie)

    assertTrue(viewModel.state.value.isEmptyNextResponse)
  }

  @Test
  fun `failed fetch movie`() = runTest {
    val msg = "error fetch movie"
    val result = Result.Error(msg)

    val genreId = 1
    val page = 1

    whenever(getMovieByGenreUseCase.invoke(genreId, page)).thenReturn(result)

    viewModel.setGenreId(genreId)
    viewModel.intents.send(MoviesByGenreIntent.FetchMovie)

    verify(navigationService).navigateToErrorMessage(msg)
    assertTrue(!viewModel.state.value.isLoading)
  }
}
