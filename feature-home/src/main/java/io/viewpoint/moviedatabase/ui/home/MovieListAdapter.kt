package io.viewpoint.moviedatabase.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.home.R
import io.viewpoint.moviedatabase.extensions.dp
import io.viewpoint.moviedatabase.home.databinding.ItemHomeMovieListBinding
import io.viewpoint.moviedatabase.util.SpaceItemDecoration

class MovieListAdapter(
    private val subAdapter: HomeMovieListAdapter
) : RecyclerView.Adapter<MovieListAdapter.MovieListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder =
        MovieListHolder(
            DataBindingUtil.inflate<ItemHomeMovieListBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_home_movie_list,
                parent,
                false
            ).also { binding ->
                binding.list.addItemDecoration(SpaceItemDecoration(16.dp))
            }
        )

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        with(holder.binding) {
            adapter = subAdapter
            executePendingBindings()
        }
    }

    class MovieListHolder(val binding: ItemHomeMovieListBinding) :
        RecyclerView.ViewHolder(binding.root)
}