package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.repositories.interfaces.ActorsRepository
import com.example.homework_2_mts.repository.retrofit.ApiService
import com.example.homework_2_mts.repository.retrofit.entities.actors.MovieCreditsApiResponse
import retrofit2.Response

class ActorsRepositoryImpl(private val apiService: ApiService): ActorsRepository {

    // API methods
    override suspend fun getActorsAPI(movieId: Long): Response<MovieCreditsApiResponse> =
        apiService.getMovieCredits(
            movieId,
            App.applicationContext.getString(R.string.api_key),
            "ru-RU"
        )

}