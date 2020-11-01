package io.viewpoint.moviedatabase.platform.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ItemGenreBinding
import io.viewpoint.moviedatabase.model.api.MovieDetail

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    private val items = mutableListOf<MovieDetail.Genre>()

    fun updateGenres(genres: List<MovieDetail.Genre>) {
        items.clear()
        items.addAll(genres)

        notifyDataSetChanged()
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
            genreName = items[position].name
            executePendingBindings()
        }

    override fun getItemCount(): Int = items.size

    class GenreViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)
}