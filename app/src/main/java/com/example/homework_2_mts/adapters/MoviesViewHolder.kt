package com.example.homework_2_mts.adapters

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

    var movieItemRoot: ConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.movieItemRoot)

    fun bind(movieDto: MovieDto) {
        Picasso.get().load(movieDto.imageUrl).into(itemView.findViewById<ImageView>(R.id.movie_poster_img));
        itemView.findViewById<TextView>(R.id.movieTitleText).text = movieDto.title
        itemView.findViewById<TextView>(R.id.movieDescriptionText).text = movieDto.description
        itemView.findViewById<TextView>(R.id.movieAgeText).text = movieDto.ageRestriction.toString() + '+'
        itemView.findViewById<RatingBar>(R.id.movieRatingLayout).rating = movieDto.rateScore.toFloat()
    }

}