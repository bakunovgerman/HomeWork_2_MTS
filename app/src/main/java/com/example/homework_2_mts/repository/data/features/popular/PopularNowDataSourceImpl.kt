package com.example.homework_2_mts.repository.data.features.popular

import com.example.homework_2_mts.repository.database.entities.GenreEntity

class PopularNowDataSourceImpl : PopularNowDataSource {
    override fun getPopularNow() = listOf(
        GenreEntity("боевики"),
        GenreEntity("драмы"),
        GenreEntity("комедии"),
        GenreEntity("артхаус"),
        GenreEntity("мелодрамы"),
        GenreEntity("ужасы"),
        GenreEntity("фантастика"),
        GenreEntity("документальные")
    )
}