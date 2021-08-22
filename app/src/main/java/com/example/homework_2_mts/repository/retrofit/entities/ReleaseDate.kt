package com.example.homework_2_mts.repository.retrofit.entities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleaseDate(
    @SerialName("certification")
    val certification: String,
    @SerialName("release_date")
    val releaseDate: String
)