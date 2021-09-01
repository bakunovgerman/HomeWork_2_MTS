package com.example.homework_2_mts.repository.database.dao

import androidx.room.*
import com.example.homework_2_mts.repository.database.entities.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieEntities: List<MovieEntity>)

    @Query("SELECT * FROM movie")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT COUNT(id) FROM movie")
    suspend fun getMoviesCount(): Int

    @Query("DELETE FROM movie")
    suspend fun clearAllDb()

}