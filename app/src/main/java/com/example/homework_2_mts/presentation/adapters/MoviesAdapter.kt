package com.example.homework_2_mts.presentation.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.presentation.adapters.view_holders.LoadingViewHolder
import com.example.homework_2_mts.presentation.adapters.view_holders.MoviesSoonViewHolder
import com.example.homework_2_mts.presentation.adapters.view_holders.MoviesViewHolder
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import java.util.*

class MoviesAdapter(private val onMovieItemClick: (MovieEntity) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: MutableList<MovieEntity> = ArrayList()

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_MOVIE_SOON
            items.size -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_MOVIE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_MOVIE_SOON -> MoviesViewHolder(
                inflater.inflate(
                    R.layout.movie_soon_item,
                    parent,
                    false
                ), onMovieItemClick
            )
            VIEW_TYPE_MOVIE -> MoviesSoonViewHolder(
                inflater.inflate(
                    R.layout.item_movie,
                    parent,
                    false
                ), onMovieItemClick
            )
            VIEW_TYPE_LOADING -> LoadingViewHolder.getViewHolder(
                inflater.inflate(
                    R.layout.loading_item,
                    parent,
                    false
                )
            )
            else -> throw IllegalStateException()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initData(movieEntities: List<MovieEntity>?) {
        items.clear()
        if (movieEntities != null) {
            items.addAll(movieEntities)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return if (items.size > 0)
            items.size + 1
        else
            0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MoviesViewHolder -> {
                holder.bind(items[position])
            }
            is MoviesSoonViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    companion object {
        const val VIEW_TYPE_MOVIE_SOON = 0
        const val VIEW_TYPE_MOVIE = 1
        const val VIEW_TYPE_LOADING = 2
    }
}