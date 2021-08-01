package com.example.homework_2_mts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.adapters.view_holders.MoviesSoonViewHolder
import com.example.homework_2_mts.adapters.view_holders.MoviesViewHolder
import com.example.homework_2_mts.data.dto.MovieDto
import java.lang.IllegalStateException

class MoviesAdapter(private val onMovieItemClick: (MovieDto) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var movieList: List<MovieDto>

    companion object {
        const val VIEW_TYPE_MOVIE_SOON = 0
        const val VIEW_TYPE_MOVIE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_MOVIE_SOON
            else -> VIEW_TYPE_MOVIE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_MOVIE_SOON -> MoviesViewHolder(inflater.inflate(R.layout.movie_soon_item, parent, false), onMovieItemClick)
            VIEW_TYPE_MOVIE -> MoviesSoonViewHolder(inflater.inflate(R.layout.movie_item, parent, false), onMovieItemClick)
            else -> throw IllegalStateException()
        }
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MoviesViewHolder -> {
                holder.bind(movieList[position])
            }
            is MoviesSoonViewHolder -> {
                holder.bind(movieList[position])
            }
        }
    }
}