package com.example.homework_2_mts.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDto(
	val title: String,
	val description: String,
	val rateScore: Int,
	val ageRestriction: Int,
	val imageUrl: String
) : Parcelable
