package com.example.homework_2_mts.repository.repositories.interfaces

import com.example.homework_2_mts.repository.retrofit.entities.actors.MovieCreditsApiResponse
import retrofit2.Response

interface ActorsRepository {
    suspend fun getActorsAPI(movieId: Long): Response<MovieCreditsApiResponse>
}