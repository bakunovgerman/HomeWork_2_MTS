package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.repository.data.features.movies.MoviesDataSourceImpl
import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.Movie
import com.example.homework_2_mts.repository.models.MoviesModel

class MovieRepository() {

    // init Dao
    private val movieDao = AppDatabase.instance.movieDao()

    // DB methods
    suspend fun getMoviesDb(): List<Movie> = movieDao.getMovies()


    suspend fun insertMoviesDb(movies: List<Movie>){
        movieDao.insertMovies(movies)
    }

    suspend fun getMoviesCountDb() : Int = movieDao.getMoviesCount()

    suspend fun clearAllDb() = movieDao.clearAllDb()


    // API methods
    fun getMoviesAPI(): List<Movie> = MoviesModel(MoviesDataSourceImpl()).getMovies()
    fun getMoviesAPIRefresh(): List<Movie> = MoviesModel(MoviesDataSourceImpl()).getMovies2()

    // equals
    suspend fun ApiEqualsDb(): Boolean{
        return getMoviesAPI() == getMoviesDb()
    }
}