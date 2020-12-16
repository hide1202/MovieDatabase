package io.viewpoint.moviedatabase.platform.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.databinding.ItemCountryBinding

private val diffCallback = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem === newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}

class CountriesAdapter : ListAdapter<String, CountriesAdapter.CountryViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(
            ItemCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            country = item
            executePendingBindings()
        }
    }

    class CountryViewHolder(
        val binding: ItemCountryBinding
    ) : RecyclerView.ViewHolder(binding.root)
}