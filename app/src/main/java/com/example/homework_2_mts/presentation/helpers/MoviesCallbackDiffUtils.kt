package com.example.homework_2_mts.presentation.helpers

import androidx.recyclerview.widget.DiffUtil
import com.example.homework_2_mts.repository.database.entities.Movie

class MoviesCallbackDiffUtils(private val oldList: List<Movie>, private val newList: List<Movie>) : DiffUtil.Callback() {
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