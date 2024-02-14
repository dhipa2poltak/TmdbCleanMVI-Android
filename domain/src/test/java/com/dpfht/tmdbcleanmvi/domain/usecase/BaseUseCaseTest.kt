package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
import org.mockito.Mock

open class BaseUseCaseTest {

  @Mock
  protected lateinit var appRepository: AppRepository
}
