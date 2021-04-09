package io.viewpoint.moviedatabase.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.ui.search.databinding.ItemRecentSearchKeywordBinding

class RecentSearchKeywordAdapter(
    private val callback: Callbacks
) : RecyclerView.Adapter<RecentSearchKeywordAdapter.ViewHolder>() {
    private val items = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateKeywords(keywords: List<String>) {
        items.clear()
        items.addAll(keywords)
        notifyDataSetChanged()
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

                callback.onRecentKeywordClick(items[position])
            }
            vh.binding.remove.setOnClickListener {
                val position = vh.bindingAdapterPosition.takeIf {
                    it != RecyclerView.NO_POSITION
                } ?: return@setOnClickListener

                val removedKeyword = items.removeAt(position)
                callback.onRemoved(removedKeyword)
                notifyItemRemoved(position)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        with(holder.binding) {
            keyword = items[position]
            executePendingBindings()
        }

    interface Callbacks {
        fun onRecentKeywordClick(keyword: String)
        fun onRemoved(keyword: String)
    }

    class ViewHolder(val binding: ItemRecentSearchKeywordBinding) :
        RecyclerView.ViewHolder(binding.root)
}