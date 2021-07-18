package com.example.homework_2_mts.adapters

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridSpacingItemDecoration() : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position: Int = parent.getChildAdapterPosition(view)
        val rvWidth: Int = view.layoutParams.width
        if (position != 0 && position % 2 != 0){
            outRect.left = rvWidth - 340
        }
        outRect.bottom = 100
    }
}
