package com.example.homework_2_mts.presentation.helpers

import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.database.entities.GenreEntity

interface MainFragmentClickListener {
    fun onOpenDetailMovieClick(movieEntity: MovieEntity)
    fun onPopularNowClick(genreEntity: GenreEntity)
    fun onOpenDetailActorClick(idActor: Long)
}