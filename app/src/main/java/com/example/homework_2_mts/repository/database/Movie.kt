package com.example.homework_2_mts.repository.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	val id: Long,
	@ColumnInfo(name = "title")
	val title: String,
	@ColumnInfo(name = "description")
	val description: String,
	@ColumnInfo(name = "rate_scope")
	val rateScore: Int,
	@ColumnInfo(name = "age_restriction")
	val ageRestriction: Int,
	@ColumnInfo(name = "image_url")
	val imageUrl: String
) : Parcelable
