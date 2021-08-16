package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.Profile

class ProfileRepository {

    // init Dao
    private val profileDao = AppDatabase.instance.profileDao()

    // init methods
    suspend fun getProfile(): Profile? = profileDao.getProfile()

    suspend fun insertProfile(profile: Profile) {
        profileDao.insertProfile(profile)
    }
}