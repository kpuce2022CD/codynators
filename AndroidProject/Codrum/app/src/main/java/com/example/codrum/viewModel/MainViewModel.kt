package com.example.codrum.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MainRepository
import com.example.data.Song
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var mainRepository: MainRepository
) : ViewModel() {

    @Inject
    lateinit var fbRdb: FirebaseDatabase

    /*  @Inject
      lateinit var fbStorage: FirebaseStorage

      @Inject
      lateinit var fbFireStore: FirebaseFirestore*/

    var song = mainRepository.allSong

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var _auth: FirebaseAuth? = null
    val auth: FirebaseAuth?
        get() = _auth

    fun getUserAuth(): FirebaseAuth? {
        _auth = Firebase.auth
        return auth
    }

/*    fun renewSongList(uid: String) {
        _isLoading.value = true
        fbRdb.getReference(uid).get().addOnSuccessListener {
            for (i in it.children) {
                if (_songList.value!!.any() { it.filename == i.key.toString() }) {
                    continue
                } else {
                    _songList.value!!.add(Song(uid, i.key.toString()))
                }
            }
            _isLoading.value = false
        }

    }*/

    fun putSong(song: Song, name: String, storage: StorageReference?, uid: String, uri: Uri?) {
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
    }
}