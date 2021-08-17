package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.repository.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.models.PopularNowModel

class GenresRepository() {

    // init Dao
    private val genreDao = AppDatabase.instance.genreDao()

    // DB methods
    suspend fun getDbGenres(): List<GenreEntity> = genreDao.getGenres()

    suspend fun insertDbGenres(genresEntityList: List<GenreEntity>) {
        genreDao.insertGenres(genresEntityList)
    }

    suspend fun getDbGenresCount(): Int = genreDao.getGenresCount()

    suspend fun clearDbAllGenres() = genreDao.clearDbAllGenres()


    // API methods
    fun getGenresAPI(): List<GenreEntity> = PopularNowModel(PopularNowDataSourceImpl()).getPopularNow()

    // equals
//    suspend fun ApiEqualsDb(): Boolean {
//        return getMoviesAPI() == getDbMovies()
//    }
}