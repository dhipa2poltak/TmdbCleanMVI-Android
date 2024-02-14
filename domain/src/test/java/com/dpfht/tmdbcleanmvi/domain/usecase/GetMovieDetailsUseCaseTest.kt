package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.entity.AppException
import com.dpfht.tmdbcleanmvi.domain.entity.GenreEntity
import com.dpfht.tmdbcleanmvi.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvi.domain.entity.Result
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
    val genres = listOf(GenreEntity(10, genreName1), GenreEntity(11, genreName2))

    val movieDetailsData = MovieDetailsDomain(
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
