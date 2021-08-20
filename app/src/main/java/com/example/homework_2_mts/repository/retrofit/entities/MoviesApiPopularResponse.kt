package com.example.homework_2_mts.repository.retrofit.entities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesApiPopularResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val moviesApiList: List<MoviesApiListItem>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)