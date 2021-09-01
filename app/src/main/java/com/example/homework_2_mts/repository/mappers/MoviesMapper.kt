package com.example.homework_2_mts.repository.mappers

import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.retrofit.entities.moviePopular.MoviesApiListItem

class MoviesMapper: BaseMapper<MoviesApiListItem, MovieEntity>() {
    override fun toEntityList(items: List<MoviesApiListItem>): List<MovieEntity> {
        return items.map { it.toMovieEntity() }
    }
}