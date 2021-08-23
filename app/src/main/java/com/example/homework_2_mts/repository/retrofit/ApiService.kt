package com.example.homework_2_mts.repository.retrofit

import com.example.homework_2_mts.repository.retrofit.entities.actors.MovieCreditsApiResponse
import com.example.homework_2_mts.repository.retrofit.entities.movieDetail.MovieDetailApiResponse
import com.example.homework_2_mts.repository.retrofit.entities.moviePopular.MoviesApiPopularResponse
import com.example.homework_2_mts.repository.retrofit.entities.releaseDate.ReleaseDateMovieApiResponse
import com.example.homework_2_mts.repository.retrofit.utils.RetrofitExtensions.Companion.addJsonConverter
import com.example.homework_2_mts.repository.retrofit.utils.RetrofitExtensions.Companion.setClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // методы
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesApiPopularResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<MovieDetailApiResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<MovieCreditsApiResponse>

    @GET("movie/{movie_id}/release_dates")
    suspend fun getReleaseDates(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): Response<ReleaseDateMovieApiResponse>

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