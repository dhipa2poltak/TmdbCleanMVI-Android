package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.AppException
import com.dpfht.tmdbcleanmvi.domain.model.Genre
import com.dpfht.tmdbcleanmvi.domain.model.MovieDetailsModel
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
class GetMovieDetailsUseCaseTest: BaseUseCaseTest() {

  private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

  @Before
  fun setup() {
    getMovieDetailsUseCase = GetMovieDetailsUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getMovieDetail method is called in AppRepository`() = runTest {
    val movieId = 101
    getMovieDetailsUseCase(movieId)
    verify(appRepository).getMovieDetail(movieId)
  }

  @Test
  fun `fetch movie details data successfully`() = runTest {
    val movieId = 1
    val title = "title1"
    val overview = "overview1"
    val posterPath = "poster_path1"
    val genreName1 = "Action"
    val genreName2 = "Drama"
    val genres = listOf(Genre(10, genreName1), Genre(11, genreName2))

    val movieDetailsData = MovieDetailsModel(
      id = movieId,
      title = title,
      overview = overview,
      imageUrl = posterPath,
      genres = genres
    )

    whenever(appRepository.getMovieDetail(movieId)).thenReturn(movieDetailsData)

    val expected = Result.Success(movieDetailsData)
    val actual = getMovieDetailsUseCase(movieId)

    assertTrue(expected == actual)
  }

  @Test
  fun `failed fetch movie details`() = runTest {
    val msg = "error fetch movie details"
    val movieId = 1

    whenever(appRepository.getMovieDetail(movieId)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = getMovieDetailsUseCase(movieId)

    assertTrue(expected == actual)
  }
}
