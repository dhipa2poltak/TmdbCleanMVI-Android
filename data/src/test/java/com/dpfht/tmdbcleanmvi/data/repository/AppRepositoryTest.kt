package com.dpfht.tmdbcleanmvi.data.repository

import com.dpfht.tmdbcleanmvi.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AppRepositoryTest {

  private lateinit var appRepository: AppRepository

  @Mock
  private lateinit var appDataSource: AppDataSource

  @Before
  fun setup() {
    appRepository = AppRepositoryImpl(appDataSource)
  }

  @Test
  fun `ensure getMovieGenre method is called in AppDataSource`() = runTest {
    appRepository.getMovieGenre()
    verify(appDataSource).getMovieGenre()
  }

  @Test
  fun `ensure getMoviesByGenre method is called in AppDataSource`() = runTest {
    val genreId = 10
    val page = 1

    appRepository.getMoviesByGenre("$genreId", page)

    verify(appDataSource).getMoviesByGenre("$genreId", page)
  }

  @Test
  fun `ensure getMovieDetail method is called in AppDataSource`() = runTest {
    val movieId = 101
    appRepository.getMovieDetail(movieId)
    verify(appDataSource).getMovieDetail(movieId)
  }

  @Test
  fun `ensure getMovieReviews method is called in AppDataSource`() = runTest {
    val movieId = 101
    val page = 1

    appRepository.getMovieReviews(movieId, page)

    verify(appDataSource).getMovieReviews(movieId, page)
  }

  @Test
  fun `ensure getMovieTrailer method is called in AppDataSource`() = runTest {
    val movieId = 101
    appRepository.getMovieTrailer(movieId)
    verify(appDataSource).getMovieTrailer(movieId)
  }
}
