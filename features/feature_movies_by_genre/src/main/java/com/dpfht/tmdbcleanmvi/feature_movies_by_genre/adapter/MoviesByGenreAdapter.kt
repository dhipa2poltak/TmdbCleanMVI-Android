package com.dpfht.tmdbcleanmvi.feature_movies_by_genre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbcleanmvi.domain.model.Movie
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.databinding.RowMovieBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject
import com.dpfht.tmdbcleanmvi.framework.R as frameworkR

class MoviesByGenreAdapter @Inject constructor(
  private val movies: ArrayList<Movie>
): RecyclerView.Adapter<MoviesByGenreAdapter.MovieByGenreHolder>() {

  var onClickMovieListener: OnClickMovieListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieByGenreHolder {
    val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return MovieByGenreHolder(binding)
  }

  override fun getItemCount(): Int {
    return movies.size
  }

  override fun onBindViewHolder(holder: MovieByGenreHolder, position: Int) {
    holder.bindData(movies[position])
    holder.itemView.setOnClickListener {
      onClickMovieListener?.onClickMovie(position)
    }
  }

  class MovieByGenreHolder(private val binding: RowMovieBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(movie: Movie) {
      binding.tvTitleMovie.text = movie.title
      binding.tvOverviewMovie.text = movie.overview

      Picasso.get().load(movie.imageUrl)
        .error(android.R.drawable.ic_menu_close_clear_cancel)
        .placeholder(frameworkR.drawable.loading)
        .into(binding.ivMovie)
    }
  }

  interface OnClickMovieListener {
    fun onClickMovie(position: Int)
  }
}
