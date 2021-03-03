package io.viewpoint.moviedatabase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.ui.search.databinding.ItemSearchResultBinding

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

class SearchResultAdapter(
    private val onClick: (ItemSearchResultBinding, SearchResultModel) -> Unit
) : PagingDataAdapter<SearchResultModel, SearchResultAdapter.SearchResultHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder =
        DataBindingUtil.inflate<ItemSearchResultBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_search_result,
            parent,
            false
        ).let { binding ->
            binding.root.setOnClickListener {
                val result = binding.model ?: return@setOnClickListener
                onClick(binding, result)
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
