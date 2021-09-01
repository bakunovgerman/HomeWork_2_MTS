package com.example.homework_2_mts.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_2_mts.presentation.fragments.ProfileFragment
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.ProfileEntity
import com.example.homework_2_mts.repository.database.entities.UpdateDbDateEntity
import com.example.homework_2_mts.repository.repositories.GenreRepositoryImpl
import com.example.homework_2_mts.repository.repositories.ProfileRepositoryImpl
import com.example.homework_2_mts.repository.repositories.UpdateDbDateRepositoryImpl
import kotlinx.coroutines.*
import java.util.*

typealias ProfileFragmentInsertProfileState = ProfileFragment.InsertProfileState

class ProfileFragmentViewModel : ViewModel() {

    // init CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        _profileInsertComplete.postValue(ProfileFragmentInsertProfileState(exception = error))
    }

    // init LiveData
    val getProfileEntityInfo: LiveData<ProfileEntity?> get() = _getProfileInfo
    private val _getProfileInfo = MutableLiveData<ProfileEntity?>()

    val profileInsertComplete: LiveData<ProfileFragmentInsertProfileState> get() = _profileInsertComplete
    private val _profileInsertComplete = MutableLiveData<ProfileFragmentInsertProfileState>()

    val genresList: LiveData<List<GenreEntity>> get() = _genresList
    private val _genresList = MutableLiveData<List<GenreEntity>>()

    // init Repositories
    private val profileRepository = ProfileRepositoryImpl()
    private val genreRepository = GenreRepositoryImpl()
    private val updateDbDateRepository = UpdateDbDateRepositoryImpl()

    private fun loadGenres(): Job {
        return viewModelScope.launch {
            delay(2000)
            withContext(Dispatchers.IO) {
                if (updateDbDateRepository.getUpdateDbDateCount() == 0 || updateDbDateRepository.isUpdateDb()) {
                    genreRepository.insertDbGenres(genreRepository.getGenresAPI())
                    updateDbDateRepository.insertDate(UpdateDbDateEntity(1, Date().time))
                }
                _genresList.postValue(genreRepository.getDbGenres())
            }
        }
    }

    fun getProfile() {
        viewModelScope.launch {
            val jobLoadGenres = loadGenres()
            jobLoadGenres.join()
            withContext(Dispatchers.IO) {
                val profile = profileRepository.getProfile()
                _getProfileInfo.postValue(profile)
            }
        }
    }

    fun insertProfile(profileEntity: ProfileEntity) {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO + errorHandler) {
                profileRepository.insertProfile(profileEntity)
                _profileInsertComplete.postValue(ProfileFragmentInsertProfileState(true))
            }
        }
    }
}