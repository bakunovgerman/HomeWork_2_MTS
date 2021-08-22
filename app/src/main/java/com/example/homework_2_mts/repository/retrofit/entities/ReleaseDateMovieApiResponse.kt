package com.example.homework_2_mts.repository.retrofit.entities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleaseDateMovieApiResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("results")
    val results: List<Result>
)