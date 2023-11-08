package io.viewpoint.moviedatabase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.feature.search.R
import io.viewpoint.moviedatabase.model.ui.KeywordModel
import io.viewpoint.moviedatabase.feature.search.databinding.ItemGenreBinding

class KeywordAdapter : ListAdapter<KeywordModel, KeywordAdapter.KeywordViewHolder>(
    object : DiffUtil.ItemCallback<KeywordModel>() {
        override fun areItemsTheSame(
            oldItem: KeywordModel,
            newItem: KeywordModel
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: KeywordModel,
            newItem: KeywordModel
        ): Boolean = oldItem == newItem
    }
) {
    fun updateGenres(genres: List<KeywordModel>) {
        submitList(genres)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        return KeywordViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_genre,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) =
        with(holder.binding) {
            genreName = getItem(position).name
            executePendingBindings()
        }

    class KeywordViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)
}