package com.example.homework_2_mts.repository.database.dao

import androidx.room.*
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.MoviesWithActorsEntity

@Dao
interface MoviesWithActorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesWithActors(moviesWithActorsEntityList: List<MoviesWithActorsEntity>)

//    @Query("SELECT * FROM genres")
//    suspend fun getGenres(): List<GenreEntity>
//
//    @Query("SELECT COUNT(id) FROM genres")
//    suspend fun getGenresCount(): Int
//
//    @Query("DELETE FROM genres")
//    suspend fun clearDbAllGenres()

}