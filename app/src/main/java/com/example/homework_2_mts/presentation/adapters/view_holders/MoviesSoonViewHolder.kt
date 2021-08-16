package com.example.homework_2_mts.presentation.adapters.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.squareup.picasso.Picasso

class MoviesSoonViewHolder(view: View, onMovieItemClick: ((MovieEntity) -> Unit)) :
    RecyclerView.ViewHolder(view) {

    private val movieItemRoot: ConstraintLayout = itemView.findViewById(R.id.clMovieItemRoot)
    private val imgMoviePoster = itemView.findViewById<ImageView>(R.id.imgMoviePoster)
    private val tvMovieTitle = itemView.findViewById<TextView>(R.id.tvMovieTitle)
    private val tvMovieDescription = itemView.findViewById<TextView>(R.id.tvMovieDescription)
    private val tvMovieAge = itemView.findViewById<TextView>(R.id.tvMovieAge)
    private val rbMovie = itemView.findViewById<RatingBar>(R.id.rbMovie)

    private lateinit var item: MovieEntity

    init {
        movieItemRoot.setOnClickListener { onMovieItemClick.invoke(item) }
    }

    fun bind(movieEntity: MovieEntity) {
        item = movieEntity
        Picasso.get().load(movieEntity.imageUrl).into(imgMoviePoster)
        tvMovieTitle.text = movieEntity.title
        tvMovieDescription.text = movieEntity.description
        tvMovieAge.text = String.format(movieEntity.ageRestriction.toString() + '+')
        rbMovie.rating = movieEntity.rateScore.toFloat()
    }

}