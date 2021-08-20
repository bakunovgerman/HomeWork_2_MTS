package com.example.homework_2_mts.repository.retrofit

import com.example.homework_2_mts.repository.retrofit.entities.MoviesPopularResponse
import com.example.homework_2_mts.repository.retrofit.utils.RetrofitExtensions.Companion.setClient
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesPopularResponse>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .setClient()
                .build()
                .create(ApiService::class.java)
        }

    }

}