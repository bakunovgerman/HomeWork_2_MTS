package com.example.homework_2_mts.domain

import android.util.Log
import androidx.lifecycle.*
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.presentation.fragments.MainFragment
import com.example.homework_2_mts.repository.database.entities.UpdateDbDateEntity
import com.example.homework_2_mts.repository.models.PopularNowModel
import com.example.homework_2_mts.repository.repositories.GenreRepository
import com.example.homework_2_mts.repository.repositories.MovieRepository
import com.example.homework_2_mts.repository.repositories.UpdateDbDateRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

typealias MainFragmentViewState = MainFragment.ViewState

class MainFragmentViewModel : ViewModel() {

    // init CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
    }

    // init LiveData
    val viewState: LiveData<MainFragmentViewState> get() = _viewState
    private val _viewState = MutableLiveData<MainFragmentViewState>()

    val moviesList: LiveData<List<MovieEntity>> get() = _moviesList
    private val _moviesList = MutableLiveData<List<MovieEntity>>()

    val genresList: LiveData<List<GenreEntity>> get() = _genresList
    private val _genresList = MutableLiveData<List<GenreEntity>>()

    val updateMoviesList: LiveData<List<MovieEntity>> get() = _updateMoviesList
    private val _updateMoviesList = MutableLiveData<List<MovieEntity>>()

    // init Repositories
    private val movieRepository = MovieRepository()
    private val genreRepository = GenreRepository()
    private val updateDbDateRepository = UpdateDbDateRepository()

    fun loadData() {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                if (updateDbDateRepository.getUpdateDbDateCount() == 0 || updateDbDateRepository.isUpdateDb()) {
                    Log.d("update_dp", "database is update")
                    Log.d("update_dp", "database insert data")
                    movieRepository.insertMoviesDb(movieRepository.getMoviesAPI())
                    genreRepository.insertDbGenres(genreRepository.getGenresAPI())
                    updateDbDateRepository.insertDate(UpdateDbDateEntity(1, Date().time))
                }
                _moviesList.postValue(movieRepository.getDbMovies())
                _genresList.postValue(genreRepository.getDbGenres())
            }
            _viewState.postValue(MainFragmentViewState(isDownloaded = true))
        }
    }

    fun updateData() {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                movieRepository.clearAllDb()
                val movies = movieRepository.getMoviesAPIRefresh()
                movieRepository.insertMoviesDb(movies)
                _updateMoviesList.postValue(movieRepository.getDbMovies())
            }
            _viewState.postValue(MainFragmentViewState(isDownloaded = true))
        }
    }

}


