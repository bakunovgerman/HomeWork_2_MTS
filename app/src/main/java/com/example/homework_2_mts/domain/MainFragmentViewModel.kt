package com.example.homework_2_mts.domain

import android.util.Log
import androidx.lifecycle.*
import com.example.homework_2_mts.App
import com.example.homework_2_mts.presentation.helpers.ViewStateLayout
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.UpdateDbDateEntity
import com.example.homework_2_mts.repository.mappers.MoviesMapper
import com.example.homework_2_mts.repository.repositories.GenreRepositoryImpl
import com.example.homework_2_mts.repository.repositories.MovieRepositoryImpl
import com.example.homework_2_mts.repository.repositories.UpdateDbDateRepositoryImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainFragmentViewModel : ViewModel() {

    // init CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
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
    private val movieRepository = MovieRepositoryImpl(App.instance.apiService)
    private val genreRepository = GenreRepositoryImpl()
    private val updateDbDateRepository = UpdateDbDateRepositoryImpl()

    fun loadData() {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                val responseMoviesApi = movieRepository.getMoviesAPI()
                if (responseMoviesApi.isSuccessful) {
                    val movies = responseMoviesApi.body()?.moviesApiList ?: emptyList()

                    if (updateDbDateRepository.getUpdateDbDateCount() == 0
                        || updateDbDateRepository.isUpdateDb() && movies.isNotEmpty()
                    ) {
                        Log.d("update_dp", "database is update")
                        Log.d("update_dp", "database insert data")

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

    fun updateData() {
//        viewModelScope.launch(errorHandler) {
//            withContext(Dispatchers.IO) {
//                Thread.sleep(2000)
//                movieRepository.clearAllDb()
//                val movies = movieRepository.getMoviesAPIRefresh()
//                movieRepository.insertDbMovies(movies)
//                _updateMoviesList.postValue(movieRepository.getDbMovies())
//            }
//            _viewState.postValue(MainFragmentViewState(isDownloaded = true))
//        }
    }

}


