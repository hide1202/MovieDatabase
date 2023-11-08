package io.viewpoint.moviedatabase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.model.ui.SearchResultModel.ProductionCompany
import io.viewpoint.moviedatabase.ui.search.ProductionCompaniesAdapter.ProductionCompanyViewHolder
import io.viewpoint.moviedatabase.feature.search.databinding.ItemProductionCompanyBinding


private val diffCallback =
    object : DiffUtil.ItemCallback<ProductionCompany>() {
        override fun areItemsTheSame(
            oldItem: ProductionCompany,
            newItem: ProductionCompany
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ProductionCompany,
            newItem: ProductionCompany
        ): Boolean = oldItem == newItem
    }

class ProductionCompaniesAdapter :
    ListAdapter<ProductionCompany, ProductionCompanyViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductionCompanyViewHolder {
        return ProductionCompanyViewHolder(
            ItemProductionCompanyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductionCompanyViewHolder, position: Int) {
        with(holder.binding) {
            company = getItem(position)
            executePendingBindings()
        }
    }

    class ProductionCompanyViewHolder(
        val binding: ItemProductionCompanyBinding
    ) : RecyclerView.ViewHolder(binding.root)
}