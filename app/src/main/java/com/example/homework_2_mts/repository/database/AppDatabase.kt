package com.example.homework_2_mts.repository.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homework_2_mts.App
import com.example.homework_2_mts.repository.database.dao.*
import com.example.homework_2_mts.repository.database.entities.*

@Database(
    entities = [
        MovieEntity::class,
        ProfileEntity::class,
        GenreEntity::class,
        UpdateDbDateEntity::class,
        MoviesWithActorsEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun profileDao(): ProfileDao
    abstract fun genreDao(): GenreDao
    abstract fun updateDbDateDao(): UpdateDbDateDao
    abstract fun moviesWithActorsDao(): MoviesWithActorsDao

    companion object {

        private const val DATABASE_NAME = "MyDatabase.db"

        val instance: AppDatabase by lazy {
            Room.databaseBuilder(
                App.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

    }
}