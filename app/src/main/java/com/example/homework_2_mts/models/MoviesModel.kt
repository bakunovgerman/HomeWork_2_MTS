package com.example.homework_2_mts.models

import com.example.homework_2_mts.data.features.movies.MoviesDataSource

class MoviesModel(
    private val moviesDataSource: MoviesDataSource
) {

    fun getMovies() = moviesDataSource.getMovies()
    fun getMovies2() = moviesDataSource.getMovies2()

}
