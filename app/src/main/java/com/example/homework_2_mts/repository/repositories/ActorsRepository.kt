package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.retrofit.entities.actors.MovieCreditsApiResponse
import retrofit2.Response

class ActorsRepository() {

    // API methods
    suspend fun getActorsAPI(movieId: Long): Response<MovieCreditsApiResponse> =
        App.instance.apiService.getMovieCredits(
            movieId,
            App.applicationContext.getString(R.string.api_key),
            "ru-RU"
        )


}