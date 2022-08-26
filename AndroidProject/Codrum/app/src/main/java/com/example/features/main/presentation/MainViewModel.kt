package com.example.features.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.features.main.data.dto.Song
import com.example.features.main.domain.DeleteUseCase
import com.example.features.main.domain.GetSongUseCase
import com.example.features.main.domain.InsertUserCase
import com.example.features.main.domain.UploadUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteUseCase: DeleteUseCase,
    private val getSonUseCase: GetSongUseCase,
    private val insertUserCase: InsertUserCase,
    private val uploadUseCase: UploadUseCase,
    private val fbRdb: FirebaseDatabase,
    private val fbAuth: FirebaseAuth,
    private val fbStore: FirebaseStorage
) : ViewModel() {

    private val _practiceSong = MutableStateFlow<List<Song>>(emptyList())
    val practiceSong = _practiceSong.asStateFlow()

    private val _mySong = MutableStateFlow<List<Song>>(emptyList())
    val mySong = _mySong.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun upload(song: Song) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _isLoading.value = true
            }
            /*uploadUseCase(song)*/
        }
    }

    fun getPractice() {
        viewModelScope.launch {
            _practiceSong.value = getSonUseCase()
        }
    }

    /*fun putSong(song: Song, name: String, storage: StorageReference?, uid: String, uri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            runCatching {
//                mainRepository.putSong(song)
            }.onSuccess {
                val map = mapOf(
                    "data" to ""
                )
                fbRdb.getReference(uid).child(name).setValue(map)
                storage?.putFile(uri!!)?.addOnSuccessListener {
                    insertSong(song)
                    _isLoading.postValue(false)
                }
            }.onFailure {
                _isLoading.postValue(false)
            }.also {
                _isLoading.postValue(false)
            }
        }
    }

    private fun insertSong(song: Song) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.insert(song)
    }

    fun deleteSong(song: Song) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.delete(song)
    }*/
}