package io.viewpoint.moviedatabase.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.home.databinding.ItemHomeMovieBinding
import io.viewpoint.moviedatabase.model.ui.HomeMovieListResultModel

class HomeMovieListAdapter(
    private val circle: Boolean = false,
    private val callback: Callback
) : ListAdapter<HomeMovieListResultModel, HomeMovieListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<HomeMovieListResultModel>() {
        override fun areItemsTheSame(
            oldItem: HomeMovieListResultModel,
            newItem: HomeMovieListResultModel
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: HomeMovieListResultModel,
            newItem: HomeMovieListResultModel
        ): Boolean = oldItem == newItem
    }
) {
    fun updateResults(results: List<HomeMovieListResultModel>) {
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
                callback.onMovieClicked(getItem(position).id)
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
        fun onMovieClicked(movieId: Int)
    }
}