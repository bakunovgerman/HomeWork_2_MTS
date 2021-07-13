package com.example.homework_2_mts.data.features.popular

import com.example.homework_2_mts.data.dto.PopularNowDto

class PopularNowDataSourceImpl : PopularNowDataSource{
    override fun getPopularNow() = listOf(
        PopularNowDto("боевики"),
        PopularNowDto("драмы"),
        PopularNowDto("комедии"),
        PopularNowDto("артхаус"),
        PopularNowDto("мелодрамы")
    )
}