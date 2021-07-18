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

    private var context: Context? = null
    public var movieItemRoot: ConstraintLayout? = null
    private var moviePosterImg: ImageView? = null
    private var movieTitleText: TextView? = null
    private var movieDecorationText: TextView? = null
    private var ageRatingText: TextView? = null
    private var movieRatingLayout: RatingBar? = null

    init {
        movieItemRoot = itemView.findViewById(R.id.movieItemRoot)
        moviePosterImg = itemView.findViewById(R.id.movie_poster_img)
        context = moviePosterImg?.context
        movieTitleText = itemView.findViewById(R.id.movieTitleText)
        movieDecorationText = itemView.findViewById(R.id.movieDescriptionText)
        ageRatingText = itemView.findViewById(R.id.movieAgeText)
        movieRatingLayout = itemView.findViewById(R.id.movieRatingLayout)
        movieRatingLayout?.numStars = 5
    }

    fun bind(movieDto: MovieDto) {
        Picasso.get().load(movieDto.imageUrl).into(moviePosterImg);
        movieTitleText?.text = movieDto.title
        movieDecorationText?.text = movieDto.description
        ageRatingText?.text = movieDto.ageRestriction.toString() + '+'
        movieRatingLayout?.rating = movieDto.rateScore.toFloat()
    }

}