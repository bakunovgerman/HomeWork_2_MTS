package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.database.entities.MoviesWithActorsEntity
import com.example.homework_2_mts.repository.repositories.interfaces.MovieRepository
import com.example.homework_2_mts.repository.retrofit.ApiService
import com.example.homework_2_mts.repository.retrofit.entities.moviePopular.MoviesApiPopularResponse
import retrofit2.Response

class MovieRepositoryImpl(private val apiService: ApiService): MovieRepository {

    // init Dao
    private val movieDao = AppDatabase.instance.movieDao()
    private val moviesWithActorsDao = AppDatabase.instance.moviesWithActorsDao()

    // DB methods
    override suspend fun getDbMovies(): List<MovieEntity> = movieDao.getMovies()

    override suspend fun insertDbMovies(movieEntities: List<MovieEntity>) {
        movieDao.insertMovies(movieEntities)
    }

    override suspend fun getMoviesCountDb(): Int = movieDao.getMoviesCount()

    override suspend fun clearAllDb() = movieDao.clearAllDb()

    override suspend fun insertDbMoviesWithActors(moviesWithActorsList: List<MoviesWithActorsEntity>) {
        moviesWithActorsDao.insertMoviesWithActors(moviesWithActorsList)
    }

    // API methods
    override suspend fun getMoviesAPI(): Response<MoviesApiPopularResponse> =
        apiService.getPopularMovies(
            App.applicationContext.getString(
                R.string.api_key
            ),
            "ru-RU",
            1
        )

    override suspend fun getReleaseDates(movieId: Long) = apiService.getReleaseDates(
        movieId,
        App.applicationContext.getString(
            R.string.api_key
        )
    )

    override suspend fun getDetail(movieId: Long) = apiService.getDetailMovie(
        movieId,
        App.applicationContext.getString(
            R.string.api_key
        ),
        "ru-RU"
    )

    //fun getMoviesAPIRefresh(): List<MovieEntity> = MoviesModel(MoviesDataSourceImpl()).getMovies2()

}