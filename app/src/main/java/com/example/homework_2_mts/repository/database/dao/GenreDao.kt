package com.example.homework_2_mts.repository.database.dao

import androidx.room.*
import com.example.homework_2_mts.repository.database.entities.GenreEntity

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genresEntity: List<GenreEntity>)

    @Query("SELECT * FROM genres")
    suspend fun getGenres(): List<GenreEntity>

    @Query("SELECT COUNT(id) FROM genres")
    suspend fun getGenresCount(): Int

    @Query("DELETE FROM genres")
    suspend fun clearDbAllGenres()

}