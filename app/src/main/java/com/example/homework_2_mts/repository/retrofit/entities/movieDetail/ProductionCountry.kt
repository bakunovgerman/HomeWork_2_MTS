package com.example.homework_2_mts.repository.retrofit.entities.movieDetail


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso31661: String,
    @SerialName("name")
    val name: String
)