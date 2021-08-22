package com.example.homework_2_mts.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_with_actors")
data class MoviesWithActorsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "actor_id")
    val actorId: Long
)