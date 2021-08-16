package com.example.homework_2_mts.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.squareup.picasso.Picasso

class MovieDetailFragment : Fragment() {

    private var movieEntity: MovieEntity? = null
    private lateinit var tvMovieTitle: TextView
    private lateinit var movieRatingLayout: RatingBar
    private lateinit var tvMovieAge: TextView
    private lateinit var tvMovieDescription: TextView
    private lateinit var imgMoviePoster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieEntity = requireArguments().getParcelable<MovieEntity>(MOVIE_KEY)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)

        tvMovieTitle = view.findViewById(R.id.tvMovieTitle)
        movieRatingLayout = view.findViewById(R.id.rbMovie)
        tvMovieAge = view.findViewById(R.id.tvMovieAge)
        tvMovieDescription = view.findViewById(R.id.tvMovieDescription)
        imgMoviePoster = view.findViewById(R.id.imgMoviePoster)
        // set text
        tvMovieTitle.text = movieEntity!!.title
        movieRatingLayout.rating = movieEntity!!.rateScore.toFloat()
        tvMovieAge.text = String.format(movieEntity!!.ageRestriction.toString() + '+')
        tvMovieDescription.text = movieEntity!!.description
        Picasso.get().load(movieEntity!!.imageUrl).into(imgMoviePoster)

        return view
    }

    companion object {
        const val MOVIE_KEY = "movie"
    }

}