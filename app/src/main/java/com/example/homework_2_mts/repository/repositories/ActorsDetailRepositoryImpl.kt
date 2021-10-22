package com.example.homework_2_mts.repository.repositories

import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.repositories.interfaces.ActorsDetailRepository
import com.example.homework_2_mts.repository.retrofit.ApiService
import com.example.homework_2_mts.repository.retrofit.entities.actorDetail.ActorDetailResponse
import retrofit2.Response

class ActorsDetailRepositoryImpl(private val apiService: ApiService): ActorsDetailRepository {
    override suspend fun getActorsDetailApi(actorId: Long): Response<ActorDetailResponse> {
        return apiService.getActorDetail(
            actorId,
            App.applicationContext.getString(
                R.string.api_key
            ),
            "ru-RU"
        )
    }
}