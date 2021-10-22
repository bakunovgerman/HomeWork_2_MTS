package com.example.homework_2_mts.repository.repositories.interfaces

import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.retrofit.entities.actorDetail.ActorDetailResponse
import retrofit2.Response

interface ActorsDetailRepository {
    suspend fun getActorsDetailApi(actorId: Long): Response<ActorDetailResponse>
}