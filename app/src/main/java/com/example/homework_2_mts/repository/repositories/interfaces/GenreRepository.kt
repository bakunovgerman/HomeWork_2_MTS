package com.example.homework_2_mts.repository.repositories.interfaces

import com.example.homework_2_mts.repository.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.models.PopularNowModel

interface GenreRepository {
    // DB methods
    suspend fun getDbGenres(): List<GenreEntity>

    suspend fun insertDbGenres(genresEntityList: List<GenreEntity>)

    suspend fun getDbGenresCount(): Int

    suspend fun clearDbAllGenres()


    // API methods
    fun getGenresAPI(): List<GenreEntity>
}