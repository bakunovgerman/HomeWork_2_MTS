package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.repository.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.models.PopularNowModel
import com.example.homework_2_mts.repository.repositories.interfaces.GenreRepository

class GenreRepositoryImpl: GenreRepository {

    // init Dao
    private val genreDao = AppDatabase.instance.genreDao()

    // DB methods
    override suspend fun getDbGenres(): List<GenreEntity> = genreDao.getGenres()

    override suspend fun insertDbGenres(genresEntityList: List<GenreEntity>) {
        genreDao.insertGenres(genresEntityList)
    }

    override suspend fun getDbGenresCount(): Int = genreDao.getGenresCount()

    override suspend fun clearDbAllGenres() = genreDao.clearDbAllGenres()

    // API methods
    override fun getGenresAPI(): List<GenreEntity> = PopularNowModel(PopularNowDataSourceImpl()).getPopularNow()

}