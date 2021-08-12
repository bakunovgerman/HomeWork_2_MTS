package com.example.homework_2_mts.repository.data.features.popular

import com.example.homework_2_mts.repository.data.dto.PopularNowDto

interface PopularNowDataSource {
    fun getPopularNow(): List<PopularNowDto>
}