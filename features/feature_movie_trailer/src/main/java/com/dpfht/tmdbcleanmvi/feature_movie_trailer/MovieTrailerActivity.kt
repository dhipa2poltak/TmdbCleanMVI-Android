package com.dpfht.tmdbcleanmvi.feature_movie_trailer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dpfht.tmdbcleanmvi.feature_movie_trailer.databinding.ActivityMovieTrailerBinding
import com.dpfht.tmdbcleanmvi.feature_movie_trailer.di.MovieTrailerModule
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import kotlinx.coroutines.launch
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class MovieTrailerActivity: AppCompatActivity() {

  private lateinit var binding: ActivityMovieTrailerBinding
  private val viewModel by inject<MovieTrailerViewModel>()

  private lateinit var youTubePlayer: YouTubePlayer

  override fun onCreate(savedInstanceState: Bundle?) {
    KTP.openRootScope()
      .openSubScope("APPSCOPE")
      .openSubScope("ActivityScope")
      .openSubScope(this)
      .installModules(MovieTrailerModule())
      .inject(this)

    super.onCreate(savedInstanceState)
    binding = ActivityMovieTrailerBinding.inflate(layoutInflater)
    setContentView(binding.root)

    lifecycleScope.launch {
      viewModel.state.collect {
        render(it)
      }
    }

    if (intent.hasExtra("movie_id")) {
      val movieId = intent.getIntExtra("movie_id", -1)

      viewModel.setMovieId(movieId)

      lifecycleScope.launch {
        viewModel.intents.send(MovieTrailerIntent.FetchTrailer)
      }
    }
  }

  private fun render(state: MovieTrailerState) {
    with(state) {
      if (keyVideo.isNotEmpty()) {
        showTrailer(keyVideo)
      }

      showErrorMessage(errorMessage)
    }
  }
  private fun showTrailer(keyVideo: String) {
    val iFramePlayerOptions = IFramePlayerOptions.Builder()
      .controls(1)
      .build()

    binding.youtubePlayerView.enableAutomaticInitialization = false
    binding.youtubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
      override fun onReady(youTubePlayer: YouTubePlayer) {
        super.onReady(youTubePlayer)

        this@MovieTrailerActivity.youTubePlayer = youTubePlayer
        youTubePlayer.loadVideo(keyVideo, 0f)
      }
    }, iFramePlayerOptions)
  }

  private fun showErrorMessage(message: String) {
    if (message.isNotEmpty()) {
      Toast.makeText(this@MovieTrailerActivity, message, Toast.LENGTH_SHORT).show()
    }
  }

  override fun onResume() {
    super.onResume()
    supportActionBar?.hide()
  }

  override fun onPause() {
    super.onPause()
    supportActionBar?.show()
  }
}
