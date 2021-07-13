package com.example.homework_2_mts.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val spaceRight: Int = 0,
                           private val spaceBottom: Int = 0,
                           private val spaceLeft: Int = 0) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = spaceRight
        outRect.bottom = spaceBottom
        outRect.left = spaceLeft
    }
}