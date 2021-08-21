package com.example.homework_2_mts.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_2_mts.App
import com.example.homework_2_mts.presentation.fragments.ProfileFragment
import com.example.homework_2_mts.repository.database.entities.ActorEntity
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.ProfileEntity
import com.example.homework_2_mts.repository.database.entities.UpdateDbDateEntity
import com.example.homework_2_mts.repository.mappers.ActorsMapper
import com.example.homework_2_mts.repository.repositories.ActorsRepository
import com.example.homework_2_mts.repository.repositories.GenreRepository
import com.example.homework_2_mts.repository.repositories.ProfileRepository
import com.example.homework_2_mts.repository.repositories.UpdateDbDateRepository
import kotlinx.coroutines.*
import java.util.*

class MovieDetailFragmentViewModel() : ViewModel() {

    // init CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, error ->

    }

    // init LiveData
    val getActors: LiveData<List<ActorEntity>> get() = _getActors
    private val _getActors = MutableLiveData<List<ActorEntity>>()

    // init Repositories
    private val actorsRepository = ActorsRepository()

    fun loadActors(movieId: Long) {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                val actorsResponse = actorsRepository.getActorsAPI(movieId)
                if (actorsResponse.isSuccessful){
                    val actorsList = actorsResponse.body()?.actorsApi ?: emptyList()
                    _getActors.postValue(ActorsMapper.toActorEntityList(actorsList))
                }
            }
        }
    }

}