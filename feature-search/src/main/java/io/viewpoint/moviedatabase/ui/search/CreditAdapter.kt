package io.viewpoint.moviedatabase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.model.ui.CreditModel
import io.viewpoint.moviedatabase.ui.search.databinding.ItemCreditBinding

private val diffCallback =
    object : DiffUtil.ItemCallback<CreditModel>() {
        override fun areItemsTheSame(
            oldItem: CreditModel,
            newItem: CreditModel
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CreditModel,
            newItem: CreditModel
        ): Boolean = oldItem == newItem
    }

class CreditAdapter : ListAdapter<CreditModel, CreditAdapter.CreditViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
        return CreditViewHolder(
            ItemCreditBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        with(holder.binding) {
            credit = getItem(position)
            executePendingBindings()
        }
    }

    class CreditViewHolder(
        val binding: ItemCreditBinding
    ) : RecyclerView.ViewHolder(binding.root)
}