package io.viewpoint.moviedatabase.util

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    @Px private val spacing: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val lm = parent.layoutManager as? LinearLayoutManager ?: return
        val position = parent.getChildAdapterPosition(view)
        val horizontalSpacing =
            if (lm.orientation == LinearLayoutManager.HORIZONTAL) spacing / 2 else 0
        val verticalSpacing =
            if (lm.orientation == LinearLayoutManager.VERTICAL) spacing / 2 else 0

        outRect.set(
            (when (position) {
                0 -> LocalizedRect(
                    start = 0,
                    top = 0,
                    end = horizontalSpacing,
                    bottom = verticalSpacing
                )
                lm.itemCount - 1 -> LocalizedRect(
                    start = horizontalSpacing,
                    top = verticalSpacing,
                    end = 0,
                    bottom = 0
                )
                else -> LocalizedRect(
                    start = horizontalSpacing,
                    top = verticalSpacing,
                    end = horizontalSpacing,
                    bottom = verticalSpacing
                )
            }).toRect()
        )
    }
}