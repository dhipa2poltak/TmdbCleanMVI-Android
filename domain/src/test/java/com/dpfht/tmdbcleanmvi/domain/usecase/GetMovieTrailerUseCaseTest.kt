package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.AppException
import com.dpfht.tmdbcleanmvi.domain.model.TrailerModel
import com.dpfht.tmdbcleanmvi.domain.model.Trailer
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
class GetMovieTrailerUseCaseTest: BaseUseCaseTest() {

  private lateinit var getMovieTrailerUseCase: GetMovieTrailerUseCase

  @Before
  fun setup() {
    getMovieTrailerUseCase = GetMovieTrailerUseCaseImpl(appRepository)
  }

  @Test
  fun `ensure getMovieTrailer method is called in AppRepository`() = runTest {
    val movieId = 101
    getMovieTrailerUseCase(movieId)
    verify(appRepository).getMovieTrailer(movieId)
  }

  @Test
  fun `fetch movie trailer successfully`() = runTest {
    val keyVideo1 = "11111"
    val trailer1 = Trailer(id = "1", key = keyVideo1, name = "name1", site = "youtube")
    val trailer2 = Trailer(id = "2", key = "22222", name = "name2", site = "youtube")
    val trailer3 = Trailer(id = "3", key = "33333", name = "name3", site = "youtube")

    val trailers = listOf(trailer1, trailer2, trailer3)
    val getMovieTrailerData = TrailerModel(results = trailers)

    val movieId = 1

    whenever(appRepository.getMovieTrailer(movieId)).thenReturn(getMovieTrailerData)

    val expected = Result.Success(getMovieTrailerData)
    val actual = getMovieTrailerUseCase(movieId)

    assertTrue(expected == actual)
  }

  @Test
  fun `failed fetch movie trailer`() = runTest {
    val msg = "error fetch movie trailer"
    val movieId = 1

    whenever(appRepository.getMovieTrailer(movieId)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = getMovieTrailerUseCase(movieId)

    assertTrue(expected == actual)
  }
}
