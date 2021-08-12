package com.example.homework_2_mts.repository.data.features.movies

import com.example.homework_2_mts.repository.database.entities.Movie

interface MoviesDataSource {
	fun getMovies(): List<Movie>
	fun getMovies2(): List<Movie>
}