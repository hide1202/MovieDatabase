package io.viewpoint.moviedatabase.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.home.databinding.ItemHomeMovieBinding
import io.viewpoint.moviedatabase.model.ui.SearchResultModel

class HomeMovieListAdapter(
    private val circle: Boolean = false,
    private val callback: Callback
) : ListAdapter<SearchResultModel, HomeMovieListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<SearchResultModel>() {
        override fun areItemsTheSame(
            oldItem: SearchResultModel,
            newItem: SearchResultModel
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: SearchResultModel,
            newItem: SearchResultModel
        ): Boolean = oldItem == newItem
    }
) {
    fun updateResults(results: List<SearchResultModel>) {
        submitList(results)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                io.viewpoint.moviedatabase.home.R.layout.item_home_movie,
                parent,
                false
            )
        ).also { vh ->
            vh.binding.circle = circle
            vh.binding.root.setOnClickListener {
                val position = vh.bindingAdapterPosition.takeIf {
                    it != RecyclerView.NO_POSITION
                } ?: return@setOnClickListener
                callback.onMovieClicked(
                    if (circle) {
                        vh.binding.circlePoster
                    } else {
                        vh.binding.poster
                    }, getItem(position)
                )
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            model = getItem(position)
            executePendingBindings()
        }
    }

    class ViewHolder(val binding: ItemHomeMovieBinding) : RecyclerView.ViewHolder(binding.root)

    interface Callback {
        fun onMovieClicked(posterView: View, movie: SearchResultModel)
    }
}