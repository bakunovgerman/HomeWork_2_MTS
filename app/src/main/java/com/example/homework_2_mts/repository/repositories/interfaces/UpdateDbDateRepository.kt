package com.example.homework_2_mts.repository.repositories.interfaces

import com.example.homework_2_mts.repository.database.entities.UpdateDbDateEntity
import java.util.*

interface UpdateDbDateRepository {
    // DB methods
    suspend fun getUpdateDbDate(): UpdateDbDateEntity

    suspend fun insertDate(updateDbDateEntity: UpdateDbDateEntity)

    suspend fun getUpdateDbDateCount(): Int

    suspend fun clearDbAllUpdateDate()

    // equals
    suspend fun isUpdateDb(): Boolean
}