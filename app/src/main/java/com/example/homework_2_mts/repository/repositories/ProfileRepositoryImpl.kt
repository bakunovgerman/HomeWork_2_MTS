package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.ProfileEntity
import com.example.homework_2_mts.repository.repositories.interfaces.ProfileRepository

class ProfileRepositoryImpl: ProfileRepository {

    // init Dao
    private val profileDao = AppDatabase.instance.profileDao()

    // init methods
    override suspend fun getProfile(): ProfileEntity? = profileDao.getProfile()

    override suspend fun insertProfile(profileEntity: ProfileEntity) {
        profileDao.insertProfile(profileEntity)
    }
}