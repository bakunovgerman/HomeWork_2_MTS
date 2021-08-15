package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.repository.data.features.movies.MoviesDataSourceImpl
import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.models.MoviesModel

class MovieRepository() {

    // init Dao
    private val movieDao = AppDatabase.instance.movieDao()

    // DB methods
    suspend fun getDbMovies(): List<MovieEntity> = movieDao.getMovies()


    suspend fun insertMoviesDb(movieEntities: List<MovieEntity>) {
        movieDao.insertMovies(movieEntities)
    }

    suspend fun getMoviesCountDb() : Int = movieDao.getMoviesCount()

    suspend fun clearAllDb() = movieDao.clearAllDb()


    // API methods
    fun getMoviesAPI(): List<MovieEntity> = MoviesModel(MoviesDataSourceImpl()).getMovies()
    fun getMoviesAPIRefresh(): List<MovieEntity> = MoviesModel(MoviesDataSourceImpl()).getMovies2()

    // equals
    suspend fun ApiEqualsDb(): Boolean {
        return getMoviesAPI() == getDbMovies()
    }
}