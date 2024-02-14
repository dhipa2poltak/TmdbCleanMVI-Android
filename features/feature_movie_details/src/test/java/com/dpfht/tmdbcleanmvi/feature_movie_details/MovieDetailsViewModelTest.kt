package com.dpfht.tmdbcleanmvi.feature_movie_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbcleanmvi.domain.entity.GenreEntity
import com.dpfht.tmdbcleanmvi.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvi.domain.entity.Result
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieDetailsUseCase
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule = InstantTaskExecutorRule()

  private lateinit var viewModel: MovieDetailsViewModel

  @Mock
  private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

  @Mock
  private lateinit var navigationService: NavigationService

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    viewModel = MovieDetailsViewModel(getMovieDetailsUseCase, navigationService)
  }

  @Test
  fun `fetch movie details data successfully`() = runTest {
    val movieId = 1
    val title = "title1"
    val overview = "overview1"
    val posterPath = "poster_path1"
    val genreName1 = "Action"
    val genreName2 = "Drama"
    val genres = listOf(GenreEntity(10, genreName1), GenreEntity(11, genreName2))

    val getMovieDetailsResult = MovieDetailsDomain(
      id = movieId,
      title = title,
      overview = overview,
      imageUrl = posterPath,
      genres = genres
    )

    val result = Result.Success(getMovieDetailsResult)

    whenever(getMovieDetailsUseCase.invoke(movieId)).thenReturn(result)

    viewModel.setMovieId(movieId)
    viewModel.intents.send(MovieDetailsIntent.FetchDetails)

    assertTrue(viewModel.state.value.movieId == movieId)
    assertTrue(viewModel.state.value.title == title)
    assertTrue(viewModel.state.value.overview == overview)
    assertTrue(viewModel.state.value.imageUrl == posterPath)
    assertTrue(viewModel.state.value.genres.contains(genreName1))
    assertTrue(viewModel.state.value.genres.contains(genreName2))
    assertTrue(!viewModel.state.value.isLoading)
  }

  @Test
  fun `failed fetch movie details`() = runTest {
    val msg = "error fetch movie details"
    val result = Result.Error(msg)

    val movieId = 1

    whenever(getMovieDetailsUseCase.invoke(movieId)).thenReturn(result)

    viewModel.setMovieId(movieId)
    viewModel.intents.send(MovieDetailsIntent.FetchDetails)

    verify(navigationService).navigateToErrorMessage(msg)
    assertTrue(!viewModel.state.value.isLoading)
  }

  @Test
  fun `navigate to moview reviews screen`() = runTest {
    viewModel.intents.send(MovieDetailsIntent.NavigateToReviewScreen)
    verify(navigationService).navigateToMovieReviews(anyInt(), anyString())
  }

  @Test
  fun `navigate to movie trailer screen`() = runTest {
    viewModel.intents.send(MovieDetailsIntent.NavigateToTrailerScreen)
    verify(navigationService).navigateToMovieTrailer(anyInt())
  }
}
