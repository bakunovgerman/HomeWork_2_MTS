package com.example.homework_2_mts.adapters.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.data.dto.MovieDto
import com.squareup.picasso.Picasso

class MoviesSoonViewHolder(view: View, onMovieItemClick: ((MovieDto) -> Unit)) :
    RecyclerView.ViewHolder(view) {

    val movieItemRoot: ConstraintLayout = itemView.findViewById(R.id.movieItemRoot)
    private val imgMoviePoster = itemView.findViewById<ImageView>(R.id.imgMoviePoster)
    private val tvMovieTitle = itemView.findViewById<TextView>(R.id.tvMovieTitle)
    private val tvMovieDescription = itemView.findViewById<TextView>(R.id.tvMovieDescription)
    private val tvMovieAge = itemView.findViewById<TextView>(R.id.tvMovieAge)
    private val rbMovie = itemView.findViewById<RatingBar>(R.id.movieRatingLayout)

    private lateinit var item: MovieDto

    init {
        movieItemRoot.setOnClickListener { onMovieItemClick.invoke(item) }
    }

    fun bind(movieDto: MovieDto) {
        item = movieDto
        Picasso.get().load(movieDto.imageUrl).into(imgMoviePoster)
        tvMovieTitle.text = movieDto.title
        tvMovieDescription.text = movieDto.description
        tvMovieAge.text = String.format(movieDto.ageRestriction.toString() + '+')
        rbMovie.rating = movieDto.rateScore.toFloat()
    }

}