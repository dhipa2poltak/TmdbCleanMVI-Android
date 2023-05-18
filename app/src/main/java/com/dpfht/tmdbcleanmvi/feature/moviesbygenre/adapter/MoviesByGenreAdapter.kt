package com.dpfht.tmdbcleanmvi.feature.moviesbygenre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbcleanmvi.databinding.RowMovieBinding
import com.dpfht.tmdbcleanmvi.domain.entity.MovieEntity
import javax.inject.Inject

class MoviesByGenreAdapter @Inject constructor(
  private val movies: ArrayList<MovieEntity>
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

    fun bindData(movie: MovieEntity) {
      binding.tvTitleMovie.text = movie.title
    }
  }

  interface OnClickMovieListener {
    fun onClickMovie(position: Int)
  }
}
