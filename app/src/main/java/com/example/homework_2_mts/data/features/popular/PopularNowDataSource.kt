package com.example.homework_2_mts.data.features.popular

import com.example.homework_2_mts.data.dto.PopularNowDto

interface PopularNowDataSource {
    fun getPopularNow(): List<PopularNowDto>
}