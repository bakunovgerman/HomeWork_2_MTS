package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.repository.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.UpdateDbDateEntity
import com.example.homework_2_mts.repository.models.PopularNowModel
import java.util.*

class UpdateDbDateRepository() {

    // init Dao
    private val updateDbDateDao = AppDatabase.instance.updateDbDateDao()

    // DB methods
    suspend fun getUpdateDbDate(): UpdateDbDateEntity = updateDbDateDao.getUpdateDbDate()

    suspend fun insertDate(updateDbDateEntity: UpdateDbDateEntity) {
        updateDbDateDao.insertDate(updateDbDateEntity)
    }

    suspend fun getUpdateDbDateCount(): Int = updateDbDateDao.getUpdateDbDateCount()

    suspend fun clearDbAllUpdateDate() = updateDbDateDao.clearDbAllUpdateDate()

    // equals
    suspend fun isUpdateDb(): Boolean {
        return ((Date().time - updateDbDateDao.getUpdateDbDate().date) / (1000 * 60 * 60)) > 1
    }
}