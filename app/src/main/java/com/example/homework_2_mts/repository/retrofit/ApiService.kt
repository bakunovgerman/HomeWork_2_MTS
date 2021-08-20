package com.example.homework_2_mts.repository.retrofit

import com.example.homework_2_mts.repository.retrofit.entities.MoviesApiPopularResponse
import com.example.homework_2_mts.repository.retrofit.utils.RetrofitExtensions.Companion.addJsonConverter
import com.example.homework_2_mts.repository.retrofit.utils.RetrofitExtensions.Companion.setClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesApiPopularResponse>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .setClient()
                .addJsonConverter()
                .build()
                .create(ApiService::class.java)
        }

    }

}