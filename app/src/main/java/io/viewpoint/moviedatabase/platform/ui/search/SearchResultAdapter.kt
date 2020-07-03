package io.viewpoint.moviedatabase.platform.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ItemSearchResultBinding
import io.viewpoint.moviedatabase.model.ui.SearchResultModel

private val diffCallback =
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

class SearchResultAdapter :
    PagingDataAdapter<SearchResultModel, SearchResultAdapter.SearchResultHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder =
        DataBindingUtil.inflate<ItemSearchResultBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_search_result,
            parent,
            false
        ).let { binding ->
            binding.root.setOnClickListener {
                val context = it.context ?: return@setOnClickListener
                val result = binding.model ?: return@setOnClickListener
                context.startActivity(
                    SearchResultDetailActivity.intent(context, result)
                )
            }
            SearchResultHolder(binding)
        }

    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) =
        with(holder.binding) {
            model = getItem(position)
            executePendingBindings()
        }

    class SearchResultHolder(val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root)
}