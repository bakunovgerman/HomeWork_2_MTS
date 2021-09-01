package com.example.homework_2_mts.presentation.adapters.items_decoration

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(
    private val spaceRight: Float = 0f,
    private val spaceLeft: Float = 0f,
    private val size: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        when (parent.getChildAdapterPosition(view)) {
            0 -> {
                outRect.left = spaceLeft.toInt()
                outRect.right = spaceRight.toInt()
            }
            size - 1 -> outRect.right = spaceLeft.toInt()
            else -> outRect.right = spaceRight.toInt()
        }

    }

    private fun dpToPx(dp: Float): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}