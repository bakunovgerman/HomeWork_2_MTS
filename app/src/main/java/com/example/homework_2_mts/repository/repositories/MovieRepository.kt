package com.example.homework_2_mts.repository.repositories

import androidx.lifecycle.LiveData
import com.example.homework_2_mts.repository.data.features.movies.MoviesDataSourceImpl
import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.Movie
import com.example.homework_2_mts.repository.models.MoviesModel

class MovieRepository() {

    private val movieDao = AppDatabase.instance.movieDao()

    // DB methods
    suspend fun getMoviesDb(): List<Movie> = movieDao.getMovies()

    suspend fun addMoviesDb(movies: List<Movie>){
        movieDao.addMovies(movies)
    }

    // API methods
    fun getMoviesAPI(): List<Movie> = MoviesModel(MoviesDataSourceImpl()).getMovies()
    fun getMoviesAPIRefresh(): List<Movie> = MoviesModel(MoviesDataSourceImpl()).getMovies2()

}