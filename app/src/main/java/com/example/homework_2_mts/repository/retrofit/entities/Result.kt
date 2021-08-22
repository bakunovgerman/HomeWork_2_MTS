package com.example.homework_2_mts.repository.retrofit.entities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("iso_3166_1")
    val iso31661: String,
    @SerialName("release_dates")
    val releaseDates: List<ReleaseDate>
)