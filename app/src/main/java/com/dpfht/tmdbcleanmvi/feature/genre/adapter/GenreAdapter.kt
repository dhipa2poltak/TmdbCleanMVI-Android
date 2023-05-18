package com.dpfht.tmdbcleanmvi.feature.genre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbcleanmvi.databinding.RowGenreBinding
import com.dpfht.tmdbcleanmvi.domain.entity.GenreEntity
import javax.inject.Inject

class GenreAdapter @Inject constructor(
  private val genres: ArrayList<GenreEntity>
): RecyclerView.Adapter<GenreAdapter.GenreHolder>() {

  var onClickGenreListener: OnClickGenreListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder {
    val binding = RowGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return GenreHolder(binding)
  }

  override fun getItemCount(): Int {
    return genres.size
  }

  override fun onBindViewHolder(holder: GenreHolder, position: Int) {
    holder.bindData(genres[position])
    holder.itemView.setOnClickListener {
      onClickGenreListener?.onClickGenre(position)
    }
  }

  class GenreHolder(private val binding: RowGenreBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(genre: GenreEntity) {
      binding.tvGenre.text = genre.name
    }
  }

  interface OnClickGenreListener {
    fun onClickGenre(position: Int)
  }
}
