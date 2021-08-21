package com.example.homework_2_mts.repository.retrofit.entities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCreditsApiResponse(
    @SerialName("cast")
    val actorsApi: List<ActorsApi>,
    @SerialName("id")
    val id: Int
)