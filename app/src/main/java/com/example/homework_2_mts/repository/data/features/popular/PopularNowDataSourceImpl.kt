package com.example.homework_2_mts.repository.data.features.popular

import com.example.homework_2_mts.repository.database.Genre

class PopularNowDataSourceImpl : PopularNowDataSource {
    override fun getPopularNow() = listOf(
        Genre("боевики"),
        Genre("драмы"),
        Genre("комедии"),
        Genre("артхаус"),
        Genre("мелодрамы"),
        Genre("ужасы"),
        Genre("фантастика"),
        Genre("документальные")
    )
}