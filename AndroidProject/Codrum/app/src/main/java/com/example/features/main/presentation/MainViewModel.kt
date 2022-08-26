package com.example.features.main.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.App
import com.example.features.main.data.dto.Song
import com.example.features.main.data.dto.toFile
import com.example.features.main.domain.LogOutUseCase
import com.example.features.main.home.domain.GetPracticeUseCase
import com.example.features.main.profile.domain.DeleteUseCase
import com.example.features.main.profile.domain.GetMySongUseCase
import com.example.features.main.upload.domain.UploadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPracticeUseCase: GetPracticeUseCase,
    private val getMySongUseCase: GetMySongUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val uploadUseCase: UploadUseCase,
    private val deleteUseCase: DeleteUseCase,
) : ViewModel() {

    private val _practiceSong = MutableStateFlow<List<Song>>(emptyList())
    val practiceSong = _practiceSong.asStateFlow()

    val mySong = getMySongUseCase().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isSuccess = MutableSharedFlow<Boolean>()
    val isSuccess = _isSuccess.asSharedFlow()

    private val _isDelete = MutableSharedFlow<Boolean>()
    val isDelete = _isDelete.asSharedFlow()

    fun getPractice() {
        viewModelScope.launch {
            _practiceSong.value = getPracticeUseCase()
        }
    }

    fun upload(song: Song, uri: Uri) {
        viewModelScope.launch {
            _isLoading.value = true
            Log.d("upload", "upload: ${_isLoading.value}")
            runCatching {
                uploadUseCase(song, uri)
            }.onSuccess {
                success(true)
            }.onFailure {
                success(false)
                Log.d("upload", "upload: ${_isLoading.value}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logOutUseCase()
            App.prefs.loginCheck = false
        }
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch {
            runCatching {
                deleteUseCase(song.filename, song.toFile())
            }.onSuccess {
                delete(true)
            }.onFailure {
                delete(false)
            }
        }
    }

    private fun success(state: Boolean) {
        viewModelScope.launch {
            _isLoading.value = false
            _isSuccess.emit(state)
        }
    }

    private fun delete(state: Boolean) {
        viewModelScope.launch {
            _isLoading.value = false
            _isDelete.emit(state)
        }
    }

}