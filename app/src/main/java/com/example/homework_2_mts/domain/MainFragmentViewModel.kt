package com.example.homework_2_mts.domain

import android.widget.Toast
import androidx.lifecycle.*
import com.example.homework_2_mts.repository.data.dto.MovieDto
import com.example.homework_2_mts.repository.data.dto.PopularNowDto
import com.example.homework_2_mts.repository.data.features.movies.MoviesDataSourceImpl
import com.example.homework_2_mts.repository.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.presentation.fragments.MainFragment
import com.example.homework_2_mts.repository.models.MoviesModel
import com.example.homework_2_mts.repository.models.PopularNowModel
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

    val moviesList: LiveData<List<MovieDto>> get() = _moviesList
    private val _moviesList= MutableLiveData<List<MovieDto>>()

    val popularNowList: LiveData<List<PopularNowDto>> get() = _popularNowList
    private val _popularNowList = MutableLiveData<List<PopularNowDto>>()

    val updateMoviesList: LiveData<List<MovieDto>> get() = _updateMoviesList
    private val _updateMoviesList = MutableLiveData<List<MovieDto>>()

    // init Models
    private val popularNowModel: PopularNowModel = PopularNowModel(PopularNowDataSourceImpl())
    private val moviesModel: MoviesModel = MoviesModel(MoviesDataSourceImpl())

    fun loadData(){
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO){
                Thread.sleep(2000)
                _moviesList.postValue(moviesModel.getMovies())
                _popularNowList.postValue(popularNowModel.getPopularNow())
            }
            _viewState.postValue(MainFragmentViewState(isDownloaded = true))
        }
    }

    fun updateData(){
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO){
                Thread.sleep(2000)
                _updateMoviesList.postValue(moviesModel.getMovies2())
            }
            _viewState.postValue(MainFragmentViewState(isDownloaded = true))
        }
    }
}


