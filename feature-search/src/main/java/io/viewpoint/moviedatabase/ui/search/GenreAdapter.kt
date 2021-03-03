package io.viewpoint.moviedatabase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.ui.search.databinding.ItemGenreBinding

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