package com.example.homework_2_mts.domain

import android.util.Log
import androidx.lifecycle.*
import com.example.homework_2_mts.repository.database.entities.Movie
import com.example.homework_2_mts.repository.database.entities.Genre
import com.example.homework_2_mts.repository.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.presentation.fragments.MainFragment
import com.example.homework_2_mts.repository.models.PopularNowModel
import com.example.homework_2_mts.repository.repositories.MovieRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias MainFragmentViewState = MainFragment.ViewState

class MainFragmentViewModel: ViewModel() {
    // init CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
    }

    // init LiveData
    val viewState: LiveData<MainFragmentViewState> get() = _viewState
    private val _viewState = MutableLiveData<MainFragmentViewState>()

    val moviesList: LiveData<List<Movie>> get() = _moviesList
    private val _moviesList= MutableLiveData<List<Movie>>()

    val popularNowList: LiveData<List<Genre>> get() = _popularNowList
    private val _popularNowList = MutableLiveData<List<Genre>>()

    val updateMoviesList: LiveData<List<Movie>> get() = _updateMoviesList
    private val _updateMoviesList = MutableLiveData<List<Movie>>()

    // init Repositories
    private val movieRepository = MovieRepository()

    // init Models
    private val popularNowModel: PopularNowModel = PopularNowModel(PopularNowDataSourceImpl())

    fun loadData(){
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                if (!movieRepository.ApiEqualsDb()) {
                    Log.d("update_dp", "database is update")
                    Log.d("update_dp", "database insert data")
                    movieRepository.insertMoviesDb(movieRepository.getMoviesAPI())
                }
                val movies = movieRepository.getDbMovies()
                _moviesList.postValue(movies)
                _popularNowList.postValue(popularNowModel.getPopularNow())
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


