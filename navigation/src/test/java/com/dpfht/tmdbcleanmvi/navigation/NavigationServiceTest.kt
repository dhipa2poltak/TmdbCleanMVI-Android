package com.dpfht.tmdbcleanmvi.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NavigationServiceTest {

    private lateinit var navigationService: NavigationService

    private lateinit var navController: NavController
    private lateinit var activity: AppCompatActivity

    @Before
    fun setup() {
        navController = mock()
        activity = mock()

        navigationService = NavigationServiceImpl(navController, activity)
    }

    @Test
    fun `ensure navigate method is called in navController when calling navigateToMoviesByGender method in navigationService`() {
        val genreId = 10
        val genreName = "Action"

        navigationService.navigateToMoviesByGender(genreId, genreName)

        verify(navController).navigate(any<NavDeepLinkRequest>())
    }

    @Test
    fun `ensure navigate method is called in navController when calling navigateToMovieDetails method in navigationService`() {
        val movieId = 101
        navigationService.navigateToMovieDetails(movieId)
        verify(navController).navigate(any<NavDeepLinkRequest>())
    }

    @Test
    fun `ensure navigate method is called in navController when calling navigateToMovieReviews method in navigationService`() {
        val movieId = 101
        val movieTitle = "Movie Title"

        navigationService.navigateToMovieReviews(movieId, movieTitle)

        verify(navController).navigate(any<NavDeepLinkRequest>())
    }

    @Test
    fun `ensure startActivity method is called in activity when calling navigateToMovieTrailer method in navigationService`() {
        val movieId = 101
        navigationService.navigateToMovieTrailer(movieId)
        verify(activity).startActivity(any())
    }

    @Test
    fun `ensure navigate method is called in navController when calling navigateToErrorMessage method in navigationService`() {
        val errorMessage = "error message"
        navigationService.navigateToErrorMessage(errorMessage)
        verify(navController).navigate(any<NavDeepLinkRequest>())
    }
}
