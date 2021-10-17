package com.example.homework_2_mts.repository.database.dao

import androidx.room.*
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.UpdateDbDateEntity

@Dao
interface UpdateDbDateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDate(updateDbDateEntity: UpdateDbDateEntity)

    @Query("SELECT * FROM update_db_date")
    suspend fun getUpdateDbDate(): UpdateDbDateEntity

    @Query("SELECT COUNT(id) FROM update_db_date")
    suspend fun getUpdateDbDateCount(): Int

    @Query("DELETE FROM genres")
    suspend fun clearDbAllUpdateDate()

}