package io.viewpoint.moviedatabase.platform.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ItemPopularBinding
import io.viewpoint.moviedatabase.model.ui.PopularResultModel

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    private val items: MutableList<PopularResultModel> = mutableListOf()

    fun updateResults(results: List<PopularResultModel>) {
        items.clear()
        items.addAll(results)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        DataBindingUtil.inflate<ItemPopularBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_popular,
            parent,
            false
        ).let { binding ->
            ViewHolder(binding)
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            model = items[position]
            executePendingBindings()
        }
    }

    class ViewHolder(val binding: ItemPopularBinding) : RecyclerView.ViewHolder(binding.root)
}