package io.viewpoint.moviedatabase.platform.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ItemLabelBinding

class LabelAdapter(
    private val labelString: String
) : RecyclerView.Adapter<LabelAdapter.LabelHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelHolder =
        LabelHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_label,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: LabelHolder, position: Int) =
        with(holder.binding) {
            label.text = labelString
            executePendingBindings()
        }

    class LabelHolder(val binding: ItemLabelBinding) : RecyclerView.ViewHolder(binding.root)
}