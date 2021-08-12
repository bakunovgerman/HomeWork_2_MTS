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
import com.example.homework_2_mts.repository.data.dto.MovieDto
import com.squareup.picasso.Picasso

class MovieDetailFragment : Fragment() {

    private var movie: MovieDto? = null
    private lateinit var tvMovieTitle: TextView
    private lateinit var movieRatingLayout: RatingBar
    private lateinit var tvMovieAge: TextView
    private lateinit var tvMovieDescription: TextView
    private lateinit var imgMoviePoster: ImageView

    companion object{
        const val MOVIE_KEY = "movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = requireArguments().getParcelable<MovieDto>(MOVIE_KEY)
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
        setInfo()

        return view
    }

    private fun setInfo(){
        tvMovieTitle.text = movie!!.title
        movieRatingLayout.rating = movie!!.rateScore.toFloat()
        tvMovieAge.text = String.format(movie!!.ageRestriction.toString() + '+')
        tvMovieDescription.text = movie!!.description
        Picasso.get().load(movie!!.imageUrl).into(imgMoviePoster)
    }
}