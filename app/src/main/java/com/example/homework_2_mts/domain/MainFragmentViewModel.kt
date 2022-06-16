package com.example.homework_2_mts.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_2_mts.App
import com.example.homework_2_mts.presentation.helpers.ViewStateLayout
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.database.entities.UpdateDbDateEntity
import com.example.homework_2_mts.repository.mappers.MoviesMapper
import com.example.homework_2_mts.repository.repositories.GenreRepositoryImpl
import com.example.homework_2_mts.repository.repositories.MovieRepositoryImpl
import com.example.homework_2_mts.repository.repositories.UpdateDbDateRepositoryImpl
import com.example.homework_2_mts.repository.repositories.interfaces.GenreRepository
import com.example.homework_2_mts.repository.repositories.interfaces.MovieRepository
import com.example.homework_2_mts.repository.repositories.interfaces.UpdateDbDateRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainFragmentViewModel : ViewModel() {

    // init CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        Log.d("proverka", error.toString())
    }

    // init LiveData
    val viewStateLayout: LiveData<ViewStateLayout> get() = _viewState
    private val _viewState = MutableLiveData<ViewStateLayout>()

    val moviesList: LiveData<List<MovieEntity>> get() = _moviesList
    private val _moviesList = MutableLiveData<List<MovieEntity>>()

    val genresList: LiveData<List<GenreEntity>> get() = _genresList
    private val _genresList = MutableLiveData<List<GenreEntity>>()

    val updateMoviesList: LiveData<List<MovieEntity>> get() = _updateMoviesList
    private val _updateMoviesList = MutableLiveData<List<MovieEntity>>()

    // init Repositories
    private val movieRepository: MovieRepository = MovieRepositoryImpl(App.instance.apiService)
    private val genreRepository: GenreRepository = GenreRepositoryImpl()
    private val updateDbDateRepository: UpdateDbDateRepository = UpdateDbDateRepositoryImpl()

    fun loadData() {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                val responseMoviesApi = movieRepository.getMoviesAPI()
                if (responseMoviesApi.isSuccessful) {
                    val movies = responseMoviesApi.body()?.moviesApiList ?: emptyList()
                    if (updateDbDateRepository.getUpdateDbDateCount() == 0
                        || updateDbDateRepository.isUpdateDb() && movies.isNotEmpty()
                    ) {
                        movieRepository.insertDbMovies(MoviesMapper().toEntityList(movies))
                        genreRepository.insertDbGenres(genreRepository.getGenresAPI())
                        updateDbDateRepository.insertDate(UpdateDbDateEntity(1, Date().time))
                    }
                    _moviesList.postValue(MoviesMapper().toEntityList(movies))
                    _genresList.postValue(genreRepository.getDbGenres())
                }

            }
            _viewState.postValue(ViewStateLayout(isDownloaded = true))
        }
    }

    fun getSearchMovies(searchText: String) {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                val responseSearchMovies = movieRepository.searchMovies(searchText)
                if (responseSearchMovies.isSuccessful) {
                    val list = responseSearchMovies.body()?.moviesApiList ?: emptyList()
                    _moviesList.postValue(MoviesMapper().toEntityList(list))
                }
            }
        }
    }
}


