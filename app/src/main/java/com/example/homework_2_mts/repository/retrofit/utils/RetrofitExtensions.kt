package com.example.homework_2_mts.repository.retrofit.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RetrofitExtensions {
    companion object{

        fun Retrofit.Builder.setClient() = apply {
            val okHttpClient = OkHttpClient()
                .newBuilder()
                .build()

            this.client(okHttpClient)
        }
    }
}