package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.AppException
import com.dpfht.tmdbcleanmvi.domain.model.ReviewModel
import com.dpfht.tmdbcleanmvi.domain.model.Review
import com.dpfht.tmdbcleanmvi.domain.model.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieReviewUseCaseTest: BaseUseCaseTest() {

  private lateinit var getMovieReviewUseCase: GetMovieReviewUseCase

  @Before
  fun setup() {
    getMovieReviewUseCase = GetMovieReviewUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getMovieReviews method is called in AppRepository`() = runTest {
    val movieId = 101
    val page = 1

    getMovieReviewUseCase(movieId, page)

    verify(appRepository).getMovieReviews(movieId, page)
  }

  @Test
  fun `fetch movie review successfully`() = runTest {
    val review1 = Review(author = "author1", content = "content1")
    val review2 = Review(author = "author2", content = "content2")
    val reviews = listOf(review1, review2)

    val movieId = 1
    val page = 1

    val reviewData = ReviewModel(reviews)

    whenever(appRepository.getMovieReviews(movieId, page)).thenReturn(reviewData)

    val expected = Result.Success(reviewData)
    val actual = getMovieReviewUseCase(movieId, page)

    assertTrue(expected == actual)
  }

  @Test
  fun `failed fetch movie review`() = runTest {
    val movieId = 1
    val page = 1

    val msg = "error fetch movie review"

    whenever(appRepository.getMovieReviews(movieId, page)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = getMovieReviewUseCase(movieId, page)

    assertTrue(expected == actual)
  }
}
