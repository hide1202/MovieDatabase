package io.viewpoint.moviedatabase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.feature.search.R
import io.viewpoint.moviedatabase.feature.search.databinding.ItemGenreBinding
import io.viewpoint.moviedatabase.model.api.MovieDetail

class GenreAdapter : ListAdapter<MovieDetail.Genre, GenreAdapter.GenreViewHolder>(
    object : DiffUtil.ItemCallback<MovieDetail.Genre>() {
        override fun areItemsTheSame(
            oldItem: MovieDetail.Genre,
            newItem: MovieDetail.Genre
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MovieDetail.Genre,
            newItem: MovieDetail.Genre
        ): Boolean = oldItem == newItem
    }
) {
    fun updateGenres(genres: List<MovieDetail.Genre>) {
        submitList(genres)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_genre,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) =
        with(holder.binding) {
            genreName = getItem(position).name
            executePendingBindings()
        }

    class GenreViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)
}