package com.example.homework_2_mts.domain

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_2_mts.presentation.fragments.ProfileFragment
import com.example.homework_2_mts.repository.database.entities.Profile
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
    val profileInfo: LiveData<Profile> get() = _profileInfo
    private val _profileInfo = MutableLiveData<Profile>()

    val profileInsertComplete: LiveData<ProfileFragmentInsertProfileState> get() = _profileInsertComplete
    private val _profileInsertComplete = MutableLiveData<ProfileFragmentInsertProfileState>()

    // init Repositories
    private val profileRepository = ProfileRepository()

    fun getProfile(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val profile = profileRepository.getProfile()
                _profileInfo.postValue(profile)
            }
        }
    }

    fun insertProfile(profile: Profile){
        viewModelScope.launch {
            withContext(Dispatchers.IO + errorHandler){
                profileRepository.insertProfile(profile)
                _profileInsertComplete.postValue(ProfileFragmentInsertProfileState(true))
            }
        }
    }
}