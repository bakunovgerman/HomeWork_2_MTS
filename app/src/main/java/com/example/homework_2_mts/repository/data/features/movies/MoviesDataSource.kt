package com.example.homework_2_mts.repository.data.features.movies

import com.example.homework_2_mts.repository.data.dto.MovieDto

interface MoviesDataSource {
	fun getMovies(): List<MovieDto>
	fun getMovies2(): List<MovieDto>
	fun getMoviesSoon(): List<MovieDto>
}