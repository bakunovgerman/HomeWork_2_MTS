package com.example.homework_2_mts.repository.database.dao

import androidx.room.*
import com.example.homework_2_mts.repository.database.entities.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<Movie>

    @Query("SELECT COUNT(id) FROM movies")
    suspend fun getMoviesCount(): Int

    @Query("DELETE from movies")
    suspend fun clearAllDb()
}