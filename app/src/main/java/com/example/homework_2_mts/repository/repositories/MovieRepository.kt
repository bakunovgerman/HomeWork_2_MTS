package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.database.entities.MoviesWithActorsEntity
import com.example.homework_2_mts.repository.retrofit.entities.MoviesApiPopularResponse
import retrofit2.Response

class MovieRepository() {

    // init Dao
    private val movieDao = AppDatabase.instance.movieDao()
    private val moviesWithActorsDao = AppDatabase.instance.moviesWithActorsDao()

    // DB methods
    suspend fun getDbMovies(): List<MovieEntity> = movieDao.getMovies()

    suspend fun insertDbMovies(movieEntities: List<MovieEntity>) {
        movieDao.insertMovies(movieEntities)
    }

    suspend fun getMoviesCountDb(): Int = movieDao.getMoviesCount()

    suspend fun clearAllDb() = movieDao.clearAllDb()

    suspend fun insertDbMoviesWithActors(moviesWithActorsList: List<MoviesWithActorsEntity>) {
        moviesWithActorsDao.insertMoviesWithActors(moviesWithActorsList)
    }

    // API methods
    suspend fun getMoviesAPI(): Response<MoviesApiPopularResponse> =
        App.instance.apiService.getPopularMovies(
            App.applicationContext.getString(
                R.string.api_key
            ),
            "ru-RU",
            1
        )

    //fun getMoviesAPIRefresh(): List<MovieEntity> = MoviesModel(MoviesDataSourceImpl()).getMovies2()

}