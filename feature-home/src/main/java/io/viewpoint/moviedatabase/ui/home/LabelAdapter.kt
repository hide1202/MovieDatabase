package io.viewpoint.moviedatabase.ui.home


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.home.R
import io.viewpoint.moviedatabase.home.databinding.ItemLabelBinding

class LabelAdapter(
    private val labelString: String
) : RecyclerView.Adapter<LabelAdapter.LabelHolder>() {
    private var isEmpty = true

    @SuppressLint("NotifyDataSetChanged")
    fun updateIsEmpty(isEmpty: Boolean) {
        this.isEmpty = isEmpty
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelHolder =
        LabelHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_label,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = if (isEmpty) 0 else 1

    override fun onBindViewHolder(holder: LabelHolder, position: Int) =
        with(holder.binding) {
            label.text = labelString
            executePendingBindings()
        }

    class LabelHolder(val binding: ItemLabelBinding) : RecyclerView.ViewHolder(binding.root)
}