package com.example.homework_2_mts.repository.repositories.interfaces

import com.example.homework_2_mts.repository.database.AppDatabase
import com.example.homework_2_mts.repository.database.entities.ProfileEntity

interface ProfileRepository {

    // init methods
    suspend fun getProfile(): ProfileEntity?

    suspend fun insertProfile(profileEntity: ProfileEntity)

}