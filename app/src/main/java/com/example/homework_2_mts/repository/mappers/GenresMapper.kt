package com.example.homework_2_mts.repository.mappers

import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.retrofit.entities.movieDetail.GenreApi

class GenresMapper: BaseMapper<GenreApi, GenreEntity>() {
    override fun toEntityList(items: List<GenreApi>): List<GenreEntity> {
        return items.map { it.toGenreEntity() }
    }
}