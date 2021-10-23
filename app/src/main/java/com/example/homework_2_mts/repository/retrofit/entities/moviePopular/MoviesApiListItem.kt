package com.example.homework_2_mts.repository.retrofit.entities.moviePopular


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesApiListItem(
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("id")
    val id: Int,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("title")
    val title: String,
    @SerialName("video")
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("adult")
    val adult: Boolean
)