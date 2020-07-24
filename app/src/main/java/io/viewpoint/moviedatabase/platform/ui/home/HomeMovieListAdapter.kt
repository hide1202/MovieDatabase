package io.viewpoint.moviedatabase.platform.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ItemHomeMovieBinding
import io.viewpoint.moviedatabase.model.ui.HomeMovieListResultModel

class HomeMovieListAdapter(
    private val circle: Boolean = false
) : RecyclerView.Adapter<HomeMovieListAdapter.ViewHolder>() {
    private val items: MutableList<HomeMovieListResultModel> = mutableListOf()

    fun updateResults(results: List<HomeMovieListResultModel>) {
        items.clear()
        items.addAll(results)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        DataBindingUtil.inflate<ItemHomeMovieBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_home_movie,
            parent,
            false
        ).let { binding ->
            binding.circle = circle
            ViewHolder(
                binding
            )
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            model = items[position]
            executePendingBindings()
        }
    }

    class ViewHolder(val binding: ItemHomeMovieBinding) : RecyclerView.ViewHolder(binding.root)
}