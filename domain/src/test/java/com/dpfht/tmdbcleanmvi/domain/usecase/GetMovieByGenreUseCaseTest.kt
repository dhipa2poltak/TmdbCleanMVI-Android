package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.AppException
import com.dpfht.tmdbcleanmvi.domain.model.DiscoverMovieByGenreModel
import com.dpfht.tmdbcleanmvi.domain.model.Movie
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
class GetMovieByGenreUseCaseTest: BaseUseCaseTest() {

  private lateinit var getMovieByGenreUseCase: GetMovieByGenreUseCase

  @Before
  fun setup() {
    getMovieByGenreUseCase = GetMovieByGenreUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getMoviesByGenre method is called in AppRepository class`() = runTest {
    val genreId = 101
    val page = 1

    getMovieByGenreUseCase(genreId, page)

    verify(appRepository).getMoviesByGenre("$genreId", page)
  }

  @Test
  fun `fetch movie successfully`() = runTest {
    val movie1 = Movie(id = 1, title = "title1", overview = "overview1")
    val movie2 = Movie(id = 2, title = "title2", overview = "overview2")
    val movie3 = Movie(id = 3, title = "title3", overview = "overview3")

    val genreId = 1
    val page = 1

    val movies = listOf(movie1, movie2, movie3)
    val getMovieByGenreData = DiscoverMovieByGenreModel(page, movies)

    whenever(appRepository.getMoviesByGenre("$genreId", page)).thenReturn(getMovieByGenreData)

    val expected = Result.Success(getMovieByGenreData)
    val actual = getMovieByGenreUseCase(genreId, page)

    assertTrue(expected == actual)
  }

  @Test
  fun `failed fetch movie`() = runTest {
    val msg = "error fetch movie"

    val genreId = 1
    val page = 1

    whenever(appRepository.getMoviesByGenre("$genreId", page)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = getMovieByGenreUseCase(genreId, page)

    assertTrue(expected == actual)
  }
}
