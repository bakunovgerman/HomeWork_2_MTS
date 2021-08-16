package com.example.homework_2_mts.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework_2_mts.repository.database.entities.ProfileEntity

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile")
    suspend fun getProfile(): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profileEntity: ProfileEntity)

}