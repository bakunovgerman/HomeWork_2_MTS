package com.example.homework_2_mts.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_2_mts.App
import com.example.homework_2_mts.presentation.helpers.ViewStateLayout
import com.example.homework_2_mts.repository.repositories.ActorsDetailRepositoryImpl
import com.example.homework_2_mts.repository.repositories.interfaces.ActorsDetailRepository
import com.example.homework_2_mts.repository.retrofit.entities.actorDetail.ActorDetailResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActorDetailViewModel : ViewModel() {
    // init CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
    }

    // init LiveData
    val viewState: LiveData<ViewStateLayout> get() = _viewState
    private val _viewState = MutableLiveData<ViewStateLayout>()

    val actorDetailData: LiveData<ActorDetailResponse> get() = _actorDetailData
    private val _actorDetailData = MutableLiveData<ActorDetailResponse>()

    private val actorDetailRepository: ActorsDetailRepository =
        ActorsDetailRepositoryImpl(App.instance.apiService)

    fun getActorDetail(actorId: Long) {
        viewModelScope.launch(errorHandler + Dispatchers.IO) {
           val response = actorDetailRepository.getActorsDetailApi(actorId)
            if (response.isSuccessful) {
                _actorDetailData.postValue(response.body())
                _viewState.postValue(ViewStateLayout(isDownloaded = true))
            }
        }

    }
}