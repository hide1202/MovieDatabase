package io.viewpoint.moviedatabase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.feature.search.R
import io.viewpoint.moviedatabase.feature.search.databinding.ItemRecentSearchKeywordBinding

class RecentSearchKeywordAdapter(
    private val callback: Callbacks
) : ListAdapter<String, RecentSearchKeywordAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }
) {
    fun updateKeywords(keywords: List<String>) {
        submitList(keywords)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_recent_search_keyword,
                parent,
                false
            )
        ).also { vh ->
            vh.binding.root.setOnClickListener {
                val position = vh.bindingAdapterPosition.takeIf {
                    it != RecyclerView.NO_POSITION
                } ?: return@setOnClickListener

                callback.onRecentKeywordClick(getItem(position))
            }
            vh.binding.remove.setOnClickListener {
                val position = vh.bindingAdapterPosition.takeIf {
                    it != RecyclerView.NO_POSITION
                } ?: return@setOnClickListener

                val removedKeyword = getItem(position)
                submitList(currentList - removedKeyword)
                callback.onRemoved(removedKeyword)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        with(holder.binding) {
            keyword = getItem(position)
            executePendingBindings()
        }

    interface Callbacks {
        fun onRecentKeywordClick(keyword: String)
        fun onRemoved(keyword: String)
    }

    class ViewHolder(val binding: ItemRecentSearchKeywordBinding) :
        RecyclerView.ViewHolder(binding.root)
}