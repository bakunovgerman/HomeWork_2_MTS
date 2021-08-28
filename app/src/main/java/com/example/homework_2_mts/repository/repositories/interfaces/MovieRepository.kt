package com.example.homework_2_mts.repository.repositories.interfaces

import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.database.entities.MoviesWithActorsEntity
import com.example.homework_2_mts.repository.retrofit.entities.moviePopular.MoviesApiPopularResponse
import retrofit2.Response

interface MovieRepository {

    // DB methods
    suspend fun getDbMovies(): List<MovieEntity>

    suspend fun insertDbMovies(movieEntities: List<MovieEntity>)

    suspend fun getMoviesCountDb(): Int

    suspend fun clearAllDb()

    suspend fun insertDbMoviesWithActors(moviesWithActorsList: List<MoviesWithActorsEntity>)

    // API methods
    suspend fun getMoviesAPI(): Response<MoviesApiPopularResponse> =
        App.instance.apiService.getPopularMovies(
            App.applicationContext.getString(
                R.string.api_key
            ),
            "ru-RU",
            1
        )

    suspend fun getReleaseDates(movieId: Long) = App.instance.apiService.getReleaseDates(
        movieId,
        App.applicationContext.getString(
            R.string.api_key
        )
    )

    suspend fun getDetail(movieId: Long) = App.instance.apiService.getDetailMovie(
        movieId,
        App.applicationContext.getString(
            R.string.api_key
        ),
        "ru-RU"
    )

}