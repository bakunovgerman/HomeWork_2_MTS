package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.repository.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.UpdateDbDateEntity
import com.example.homework_2_mts.repository.models.PopularNowModel
import com.example.homework_2_mts.repository.repositories.interfaces.UpdateDbDateRepository
import java.util.*

class UpdateDbDateRepositoryImpl: UpdateDbDateRepository {

    // init Dao
    private val updateDbDateDao = AppDatabase.instance.updateDbDateDao()

    // DB methods
    override suspend fun getUpdateDbDate(): UpdateDbDateEntity = updateDbDateDao.getUpdateDbDate()

    override suspend fun insertDate(updateDbDateEntity: UpdateDbDateEntity) {
        updateDbDateDao.insertDate(updateDbDateEntity)
    }

    override suspend fun getUpdateDbDateCount(): Int = updateDbDateDao.getUpdateDbDateCount()

    override suspend fun clearDbAllUpdateDate() = updateDbDateDao.clearDbAllUpdateDate()

    // equals
    override suspend fun isUpdateDb(): Boolean {
        // если база обновлялась больше часа назад, то разрешаем обновление
        return ((Date().time - updateDbDateDao.getUpdateDbDate().date) / (1000 * 60 * 60)) > 1
    }

}