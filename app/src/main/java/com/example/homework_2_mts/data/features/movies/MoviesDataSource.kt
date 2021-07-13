package ru.mts.teta.summer.android.homework.list.data.features.movies

import com.example.homework_2_mts.data.dto.MovieDto

interface MoviesDataSource {
	fun getMovies(): List<MovieDto>
}