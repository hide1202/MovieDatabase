package io.viewpoint.moviedatabase.platform.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ItemHomeMovieBinding
import io.viewpoint.moviedatabase.model.ui.HomeMovieListResultModel

class HomeMovieListAdapter(
    private val circle: Boolean = false,
    private val callback: Callback
) : RecyclerView.Adapter<HomeMovieListAdapter.ViewHolder>() {
    private val items: MutableList<HomeMovieListResultModel> = mutableListOf()

    fun updateResults(results: List<HomeMovieListResultModel>) {
        items.clear()
        items.addAll(results)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_home_movie,
                parent,
                false
            )
        ).also { vh ->
            vh.binding.circle = circle
            vh.binding.root.setOnClickListener {
                val position = vh.bindingAdapterPosition.takeIf {
                    it != RecyclerView.NO_POSITION
                } ?: return@setOnClickListener
                callback.onMovieClicked(items[position].id)
            }
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            model = items[position]
            executePendingBindings()
        }
    }

    class ViewHolder(val binding: ItemHomeMovieBinding) : RecyclerView.ViewHolder(binding.root)

    interface Callback {
        fun onMovieClicked(movieId: Int)
    }
}