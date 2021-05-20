package io.viewpoint.moviedatabase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.ui.search.databinding.ItemRecommendBinding

class RecommendAdapter : ListAdapter<SearchResultModel, RecommendAdapter.RecommendViewHolder>(
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder =
        RecommendViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_recommend,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        with(holder.binding) {
            model = getItem(position)
            executePendingBindings()
        }
    }

    class RecommendViewHolder(val binding: ItemRecommendBinding) :
        RecyclerView.ViewHolder(binding.root)
}