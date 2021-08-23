package com.example.homework_2_mts.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_2_mts.repository.database.entities.*
import com.example.homework_2_mts.repository.mappers.ActorsMapper
import com.example.homework_2_mts.repository.mappers.GenresMapper
import com.example.homework_2_mts.repository.repositories.*
import com.example.homework_2_mts.repository.retrofit.entities.releaseDate.ReleaseDate
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class MovieDetailFragmentViewModel : ViewModel() {

    // init CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        Log.d("movieDetailApiResponse", error.toString())
    }

    // init LiveData
    val getActors: LiveData<List<ActorEntity>> get() = _getActors
    private val _getActors = MutableLiveData<List<ActorEntity>>()

    val getReleaseDate: LiveData<ReleaseDate> get() = _getReleaseDate
    private val _getReleaseDate = MutableLiveData<ReleaseDate>()

    val getGenres: LiveData<List<GenreEntity>> get() = _getGenres
    private val _getGenres = MutableLiveData<List<GenreEntity>>()

    // init Repositories
    private val actorsRepository = ActorsRepository()
    private val moviesRepository = MovieRepository()

    fun loadDetail(movieId: Long) {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                val movieDetailApiResponse = moviesRepository.getDetail(movieId)
                Log.d("movieDetailApiResponse", movieDetailApiResponse.body().toString())
                if (movieDetailApiResponse.isSuccessful) {
                    val genresList = movieDetailApiResponse.body()?.genresApi ?: emptyList()
                    Log.d("genresList", genresList.toString())
                    _getGenres.postValue(GenresMapper.toGenreEntityList(genresList))
                }
            }
        }
    }

    fun loadReleaseDates(movieId: Long) {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                val releaseDatesResponse = moviesRepository.getReleaseDates(movieId)
                if (releaseDatesResponse.isSuccessful) {
                    val releaseDateList = releaseDatesResponse.body()?.results ?: emptyList()
                    if (releaseDateList.isNotEmpty()) {
                        releaseDateList.forEach {
                            if (it.iso31661 == "RU") {
                                _getReleaseDate.postValue(it.releaseDates[0])
                                return@forEach
                            }
                        }
                    }
                }
            }
        }
    }

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