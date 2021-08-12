package com.example.homework_2_mts.presentation.helpers

import com.example.homework_2_mts.repository.database.Movie
import com.example.homework_2_mts.repository.database.Genre

interface MainFragmentClickListener {
    fun onOpenDetailMovieClick(movie: Movie)
    fun onPopularNowClick(genre: Genre)
}