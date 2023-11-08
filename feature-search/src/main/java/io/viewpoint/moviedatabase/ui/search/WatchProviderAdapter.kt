package io.viewpoint.moviedatabase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.feature.search.R
import io.viewpoint.moviedatabase.feature.search.databinding.ItemWatchProviderBinding
import io.viewpoint.moviedatabase.model.ui.WatchProviderModel

class WatchProviderAdapter :
    ListAdapter<WatchProviderModel.Info, WatchProviderAdapter.WatchProviderViewHolder>(
        object : DiffUtil.ItemCallback<WatchProviderModel.Info>() {
            override fun areItemsTheSame(
                oldItem: WatchProviderModel.Info,
                newItem: WatchProviderModel.Info
            ): Boolean = oldItem.providerId == newItem.providerId

            override fun areContentsTheSame(
                oldItem: WatchProviderModel.Info,
                newItem: WatchProviderModel.Info
            ): Boolean = oldItem == newItem

        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchProviderViewHolder =
        WatchProviderViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_watch_provider,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WatchProviderViewHolder, position: Int) {
        with(holder.binding) {
            model = getItem(position)
            executePendingBindings()
        }
    }

    class WatchProviderViewHolder(val binding: ItemWatchProviderBinding) :
        RecyclerView.ViewHolder(binding.root)
}