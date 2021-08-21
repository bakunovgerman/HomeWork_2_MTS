package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.retrofit.entities.ActorsApi
import com.example.homework_2_mts.repository.retrofit.entities.MovieCreditsApiResponse
import com.example.homework_2_mts.repository.retrofit.entities.MoviesApiPopularResponse
import retrofit2.Response

class ActorsRepository() {

    // init Dao
    //private val movieDao = AppDatabase.instance.movieDao()

    // DB methods
    //suspend fun getDbMovies(): List<MovieEntity> = movieDao.getMovies()


    // API methods
    suspend fun getActorsAPI(movieId: Long): Response<MovieCreditsApiResponse> =
        App.instance.apiService.getMovieCredits(
            movieId,
            App.applicationContext.getString(R.string.api_key)
        )

    //fun getMoviesAPIRefresh(): List<MovieEntity> = MoviesModel(MoviesDataSourceImpl()).getMovies2()

}