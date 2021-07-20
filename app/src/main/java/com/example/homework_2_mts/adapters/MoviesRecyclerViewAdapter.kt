package com.example.homework_2_mts.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.data.dto.MovieDto
import com.squareup.picasso.Picasso
import java.lang.IllegalStateException

class MoviesRecyclerViewAdapter (var list: List<MovieDto>, var onMovieItemClick: ((MovieDto) -> Unit))
    : RecyclerView.Adapter<MoviesViewHolder>(){

    private var context: Context? = null

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ACTOR = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position){
            0 -> VIEW_TYPE_HEADER
            else -> VIEW_TYPE_ACTOR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when(viewType) {
                VIEW_TYPE_HEADER ->  MoviesViewHolder(inflater.inflate(R.layout.movie_item, parent, false))
                VIEW_TYPE_ACTOR -> MoviesViewHolder(inflater.inflate(R.layout.movie_item, parent, false))
                else -> throw IllegalStateException()
            }
        }

        override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
            when(holder){
                is MoviesViewHolder -> {
                    holder.bind(list[position])
                    holder.movieItemRoot?.setOnClickListener { onMovieItemClick.invoke(list[position]) }
                }
            }
        }
        override fun getItemCount(): Int = list.size
 }