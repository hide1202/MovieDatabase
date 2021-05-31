package io.viewpoint.moviedatabase.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.ui.common.databinding.ItemSearchResultBinding

class MovieListAdapter : PagingDataAdapter<SearchResultModel, MovieListAdapter.ViewHolder>(
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search_result,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            model = getItem(position)
            executePendingBindings()
        }
    }

    class ViewHolder(val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root)
}