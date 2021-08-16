package com.example.homework_2_mts.repository.models

import com.example.homework_2_mts.repository.data.features.movies.MoviesDataSource

class MoviesModel(
    private val moviesDataSource: MoviesDataSource
) {

    fun getMovies() = moviesDataSource.getMovies()
    fun getMovies2() = moviesDataSource.getMovies2()

}
