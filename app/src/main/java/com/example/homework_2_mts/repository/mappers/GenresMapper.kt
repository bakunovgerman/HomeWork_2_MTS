package com.example.homework_2_mts.repository.mappers

import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.retrofit.entities.movieDetail.GenreApi

class GenresMapper {

    companion object {
        private fun GenreApi.toGenreEntity(): GenreEntity = GenreEntity(id, name)

        fun toGenreEntityList(items: List<GenreApi>): List<GenreEntity> {
            val list: MutableList<GenreEntity> = ArrayList()
            items.forEach {
                list.add(it.toGenreEntity())
            }
            return list.toList()
        }
    }

}