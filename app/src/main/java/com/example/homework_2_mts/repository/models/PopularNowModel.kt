package com.example.homework_2_mts.repository.models

import com.example.homework_2_mts.repository.data.features.popular.PopularNowDataSource
import com.example.homework_2_mts.repository.data.features.popular.PopularNowDataSourceImpl

class PopularNowModel(private val popularNowDataSource: PopularNowDataSourceImpl) {
    fun getPopularNow() = popularNowDataSource.getPopularNow()
}