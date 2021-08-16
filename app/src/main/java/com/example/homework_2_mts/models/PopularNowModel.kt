package com.example.homework_2_mts.models

import com.example.homework_2_mts.data.features.popular.PopularNowDataSourceImpl

class PopularNowModel(private val popularNowDataSource: PopularNowDataSourceImpl) {
    fun getPopularNow() = popularNowDataSource.getPopularNow()
}