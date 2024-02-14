package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.entity.AppException
import com.dpfht.tmdbcleanmvi.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvi.domain.entity.GenreEntity
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
class GetMovieGenreUseCaseTest: BaseUseCaseTest() {

  private lateinit var getMovieGenreUseCase: GetMovieGenreUseCase

  @Before
  fun setup() {
    getMovieGenreUseCase = GetMovieGenreUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getMovieGenre method is called in AppRepository`() = runTest {
    getMovieGenreUseCase()

    verify(appRepository).getMovieGenre()
  }

  @Test
  fun `fetch movie genre successfully`() = runTest {
    val genre1 = GenreEntity(1, "Cartoon")
    val genre2 = GenreEntity(2, "Drama")
    val genre3 = GenreEntity(3, "Horror")

    val genres = listOf(genre1, genre2, genre3)
    val getMovieGenreData = GenreDomain(genres)

    whenever(appRepository.getMovieGenre()).thenReturn(getMovieGenreData)

    val expected = Result.Success(getMovieGenreData)
    val actual = getMovieGenreUseCase()

    assertTrue(expected == actual)
  }

  @Test
  fun `failed fetch movie genre`() = runTest {
    val msg = "error fetch genre"

    whenever(appRepository.getMovieGenre()).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = getMovieGenreUseCase()

    assertTrue(expected == actual)
  }
}
