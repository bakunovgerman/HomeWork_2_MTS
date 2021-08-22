package com.example.homework_2_mts.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_2_mts.App
import com.example.homework_2_mts.presentation.fragments.ProfileFragment
import com.example.homework_2_mts.repository.database.entities.*
import com.example.homework_2_mts.repository.mappers.ActorsMapper
import com.example.homework_2_mts.repository.repositories.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class MovieDetailFragmentViewModel() : ViewModel() {

    // init CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, error ->

    }

    // init LiveData
    val getActors: LiveData<List<ActorEntity>> get() = _getActors
    private val _getActors = MutableLiveData<List<ActorEntity>>()

    // init Repositories
    private val actorsRepository = ActorsRepository()
    private val moviesRepository = MovieRepository()

    fun loadActors(movieId: Long) {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                val actorsResponse = actorsRepository.getActorsAPI(movieId)
                if (actorsResponse.isSuccessful) {
                    val actorsList = actorsResponse.body()?.actorsApi ?: emptyList()
                    // делаем проверку на пустой список чтобы понять нужно ли инсертить данные в таблицу MoviesWithActors
                    if (actorsList.isNotEmpty()) {
                        val movieWithActorList: MutableList<MoviesWithActorsEntity> = ArrayList()
                        actorsList.forEach {
                            movieWithActorList.add(
                                MoviesWithActorsEntity(
                                    movieId = it.id.toLong(),
                                    actorId = movieId
                                )
                            )
                        }
                        moviesRepository.insertDbMoviesWithActors(movieWithActorList)
                    }
                    _getActors.postValue(ActorsMapper.toActorEntityList(actorsList))
                }
            }
        }
    }

}