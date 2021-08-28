package com.example.homework_2_mts.repository.mappers

import com.example.homework_2_mts.repository.database.entities.ActorEntity
import com.example.homework_2_mts.repository.retrofit.entities.actors.ActorsApi

class ActorsMapper: BaseMapper<ActorsApi, ActorEntity>() {
    override fun toEntityList(items: List<ActorsApi>): List<ActorEntity> {
        return items.map { it.toActorsEntity() }
    }
}