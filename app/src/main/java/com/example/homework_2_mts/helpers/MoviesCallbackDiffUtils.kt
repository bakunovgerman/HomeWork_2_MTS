package com.example.homework_2_mts.helpers

import androidx.recyclerview.widget.DiffUtil
import com.example.homework_2_mts.data.dto.MovieDto

class MoviesCallbackDiffUtils(private val oldList: List<MovieDto>, private val newList: List<MovieDto>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}