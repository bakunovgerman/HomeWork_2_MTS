package com.example.homework_2_mts.repository.data.features.popular

import com.example.homework_2_mts.repository.database.entities.GenreEntity

interface PopularNowDataSource {
    fun getPopularNow(): List<GenreEntity>
}