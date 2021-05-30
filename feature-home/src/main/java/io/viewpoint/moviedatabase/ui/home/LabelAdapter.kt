package io.viewpoint.moviedatabase.ui.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.home.R
import io.viewpoint.moviedatabase.home.databinding.ItemLabelBinding

class LabelAdapter(
    private val labelString: String,
    initialVisible: Boolean = true
) : RecyclerView.Adapter<LabelAdapter.LabelHolder>() {
    var isVisible = initialVisible
        set(value) {
            if (field != value) {
                val oldValue = field

                field = value

                if (value) {
                    notifyItemInserted(0)
                } else {
                    notifyItemRemoved(0)
                }
            }
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

    override fun getItemCount(): Int = if (isVisible) 1 else 0

    override fun onBindViewHolder(holder: LabelHolder, position: Int) =
        with(holder.binding) {
            label.text = labelString
            executePendingBindings()
        }

    class LabelHolder(val binding: ItemLabelBinding) : RecyclerView.ViewHolder(binding.root)
}