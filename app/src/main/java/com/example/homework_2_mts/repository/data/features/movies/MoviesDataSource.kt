package com.example.homework_2_mts.repository.data.features.movies

import com.example.homework_2_mts.repository.database.entities.MovieEntity

interface MoviesDataSource {
    fun getMovies(): List<MovieEntity>
    fun getMovies2(): List<MovieEntity>
}