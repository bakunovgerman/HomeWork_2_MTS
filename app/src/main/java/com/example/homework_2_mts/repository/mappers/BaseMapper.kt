package com.example.homework_2_mts.repository.mappers

import com.example.homework_2_mts.repository.database.entities.ActorEntity
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.retrofit.entities.actors.ActorsApi
import com.example.homework_2_mts.repository.retrofit.entities.movieDetail.GenreApi
import com.example.homework_2_mts.repository.retrofit.entities.moviePopular.MoviesApiListItem

abstract class BaseMapper<T, A> {
    companion object {
        // методы преобразователи (extensions)
        fun ActorsApi.toActorsEntity(): ActorEntity =
            ActorEntity(
                id.toLong(),
                name,
                profilePath
            )
        fun GenreApi.toGenreEntity(): GenreEntity = GenreEntity(id, name)
        fun MoviesApiListItem.toMovieEntity(): MovieEntity =
            MovieEntity(
                id.toLong(),
                title,
                overview,
                voteAverage / 2,
                if (adult) 18 else 12,
                posterPath,
                backdropPath
            )
    }
    // общий метод для преобразования списка данных
    abstract fun toEntityList(items: List<T>): List<A>
}