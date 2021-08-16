package com.example.homework_2_mts.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_2_mts.presentation.fragments.ProfileFragment
import com.example.homework_2_mts.repository.database.entities.ProfileEntity
import com.example.homework_2_mts.repository.repositories.ProfileRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    // init Repositories
    private val profileRepository = ProfileRepository()

    fun getProfile() {
        viewModelScope.launch {
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