package com.example.homework_2_mts.repository.mappers

import com.example.homework_2_mts.repository.database.entities.ActorEntity
import com.example.homework_2_mts.repository.retrofit.entities.actors.ActorsApi

class ActorsMapper {

    companion object {
        private fun ActorsApi.toActorsEntity(): ActorEntity =
            ActorEntity(
                id.toLong(),
                name,
                profilePath
            )
        fun toActorEntityList(items: List<ActorsApi>): List<ActorEntity> {
            var list: MutableList<ActorEntity> = ArrayList()
            items.forEach {
                list.add(it.toActorsEntity())
            }
            return list.toList()
        }
    }

}