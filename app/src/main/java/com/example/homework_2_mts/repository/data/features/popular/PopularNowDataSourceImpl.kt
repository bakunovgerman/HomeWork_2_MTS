package com.example.homework_2_mts.repository.data.features.popular

import com.example.homework_2_mts.repository.database.entities.GenreEntity

class PopularNowDataSourceImpl : PopularNowDataSource {
    override fun getPopularNow() = listOf(
        GenreEntity(1,"боевики"),
        GenreEntity(2,"драмы"),
        GenreEntity(3,"комедии"),
        GenreEntity(4,"артхаус"),
        GenreEntity(5,"мелодрамы"),
        GenreEntity(6,"ужасы"),
        GenreEntity(7,"фантастика"),
        GenreEntity(8,"документальные")
    )
}