package com.dpfht.tmdbcleanmvi.feature_movie_reviews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbcleanmvi.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvi.domain.entity.ReviewEntity
import com.dpfht.tmdbcleanmvi.domain.entity.Result
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvi.feature_movie_reviews.adapter.MovieReviewsAdapter
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
class MovieReviewsViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: MovieReviewsViewModel

  @Mock
  private lateinit var adapter: MovieReviewsAdapter

  @Mock
  private lateinit var getMovieReviewUseCase: GetMovieReviewUseCase

  @Mock
  private lateinit var navigationService: NavigationService

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    viewModel = MovieReviewsViewModel(getMovieReviewUseCase, arrayListOf(), adapter, navigationService)
  }

  @Test
  fun `fetch movie review successfully`() = runTest {
    val review1 = ReviewEntity(author = "author1", content = "content1")
    val review2 = ReviewEntity(author = "author2", content = "content2")
    val reviews = listOf(review1, review2)

    val movieId = 1
    val page = 1

    val getMovieReviewResult = ReviewDomain(reviews)
    val result = Result.Success(getMovieReviewResult)

    whenever(getMovieReviewUseCase.invoke(movieId, page)).thenReturn(result)

    viewModel.setMovieId(movieId)
    viewModel.intents.send(MovieReviewsIntent.FetchReview)

    verify(adapter, times(reviews.size)).notifyItemInserted(anyInt())
    assertTrue(!viewModel.state.value.isLoading)
  }

  @Test
  fun `failed fetch movie review`() = runTest {
    val movieId = 1
    val page = 1

    val msg = "error fetch movie review"
    val result = Result.Error(msg)

    whenever(getMovieReviewUseCase.invoke(movieId, page)).thenReturn(result)

    viewModel.setMovieId(movieId)
    viewModel.intents.send(MovieReviewsIntent.FetchReview)

    verify(navigationService).navigateToErrorMessage(msg)
    assertTrue(!viewModel.state.value.isLoading)
  }
}
