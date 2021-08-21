package com.example.homework_2_mts.repository.mappers

import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.retrofit.entities.MoviesApiListItem
import com.example.homework_2_mts.repository.retrofit.entities.MoviesApiPopularResponse

class MoviesMapper {

    companion object {
        private fun MoviesApiListItem.toMovieEntity(): MovieEntity =
            MovieEntity(
                id.toLong(),
                title,
                overview,
                voteAverage / 2,
                if (adult) 18 else 12,
                posterPath,
                backdropPath
            )
        fun toMovieEntityList(items: List<MoviesApiListItem>): List<MovieEntity> {
            var list: MutableList<MovieEntity> = ArrayList()
            items.forEach {
                list.add(it.toMovieEntity())
            }
            return list.toList()
        }
    }

}