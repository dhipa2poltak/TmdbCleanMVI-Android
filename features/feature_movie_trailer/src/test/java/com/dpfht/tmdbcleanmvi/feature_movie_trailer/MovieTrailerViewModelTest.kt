package com.dpfht.tmdbcleanmvi.feature_movie_trailer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbcleanmvi.domain.entity.Result
import com.dpfht.tmdbcleanmvi.domain.entity.TrailerDomain
import com.dpfht.tmdbcleanmvi.domain.entity.TrailerEntity
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieTrailerUseCase
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
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MovieTrailerViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: MovieTrailerViewModel

  @Mock
  private lateinit var getMovieTrailerUseCase: GetMovieTrailerUseCase

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    viewModel = MovieTrailerViewModel(getMovieTrailerUseCase)
  }

  @Test
  fun `fetch movie trailer successfully`() = runTest {
    val keyVideo1 = "11111"
    val trailer1 = TrailerEntity(id = "1", key = keyVideo1, name = "name1", site = "youtube")
    val trailer2 = TrailerEntity(id = "2", key = "22222", name = "name2", site = "youtube")
    val trailer3 = TrailerEntity(id = "3", key = "33333", name = "name3", site = "youtube")

    val trailers = listOf(trailer1, trailer2, trailer3)
    val getMovieTrailerResult = TrailerDomain(results = trailers)
    val result = Result.Success(getMovieTrailerResult)

    val movieId = 1

    whenever(getMovieTrailerUseCase.invoke(movieId)).thenReturn(result)

    viewModel.setMovieId(movieId)
    viewModel.intents.send(MovieTrailerIntent.FetchTrailer)

    assertTrue(viewModel.state.value.keyVideo == keyVideo1)
  }

  @Test
  fun `failed fetch movie trailer`() = runTest {
    val msg = "error fetch movie trailer"
    val result = Result.Error(msg)

    val movieId = 1

    whenever(getMovieTrailerUseCase.invoke(movieId)).thenReturn(result)

    viewModel.setMovieId(movieId)
    viewModel.intents.send(MovieTrailerIntent.FetchTrailer)

    assertTrue(viewModel.state.value.errorMessage == msg)
  }
}
