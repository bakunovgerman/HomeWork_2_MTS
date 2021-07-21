package com.example.homework_2_mts.adapters.view_holders

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.data.dto.MovieDto
import com.squareup.picasso.Picasso

class MoviesViewHolder(inflater: View) :
    RecyclerView.ViewHolder(inflater) {

    val movieItemRoot: ConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.movieItemRoot)
    private val imgMoviePoster = itemView.findViewById<ImageView>(R.id.movie_poster_img)
    private val tvMovieTitle  = itemView.findViewById<TextView>(R.id.movieTitleText)
    private val tvMovieDescription = itemView.findViewById<TextView>(R.id.movieDescriptionText)
    private val tvMovieAge = itemView.findViewById<TextView>(R.id.movieDescriptionText)
    private val rbMovie = itemView.findViewById<RatingBar>(R.id.movieRatingLayout)

    fun bind(movieDto: MovieDto) {
        Picasso.get().load(movieDto.imageUrl).into(imgMoviePoster)
        tvMovieTitle.text = movieDto.title
        tvMovieDescription.text = movieDto.description
        tvMovieAge.text = String.format(movieDto.ageRestriction.toString() + '+')
        rbMovie.rating = movieDto.rateScore.toFloat()
    }

}