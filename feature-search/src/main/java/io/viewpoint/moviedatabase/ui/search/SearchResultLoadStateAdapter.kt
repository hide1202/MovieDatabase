package io.viewpoint.moviedatabase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.ui.search.databinding.ItemSearchLoadStateBinding

class SearchResultLoadStateAdapter : LoadStateAdapter<SearchResultLoadStateAdapter.ViewHolder>() {
    override fun onBindViewHolder(
        holder: ViewHolder,
        loadState: LoadState
    ) = with(holder.binding) {
        this.loadState = loadState
        executePendingBindings()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_search_load_state,
            parent,
            false
        )
    )

    class ViewHolder(val binding: ItemSearchLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root)
}