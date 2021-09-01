package com.example.homework_2_mts.repository.retrofit.entities.movieDetail


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreApi(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)