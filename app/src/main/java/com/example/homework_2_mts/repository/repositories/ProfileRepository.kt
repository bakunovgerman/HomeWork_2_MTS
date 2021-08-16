package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.ProfileEntity

class ProfileRepository {

    // init Dao
    private val profileDao = AppDatabase.instance.profileDao()

    // init methods
    suspend fun getProfile(): ProfileEntity? = profileDao.getProfile()

    suspend fun insertProfile(profileEntity: ProfileEntity) {
        profileDao.insertProfile(profileEntity)
    }
}