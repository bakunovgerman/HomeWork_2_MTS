package com.example.homework_2_mts.presentation.helpers

import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.database.entities.Genre

interface MainFragmentClickListener {
    fun onOpenDetailMovieClick(movieEntity: MovieEntity)
    fun onPopularNowClick(genre: Genre)
}