package com.example.codrum.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codrum.data.MainRepository
import com.example.codrum.data.Song
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var mainRepository: MainRepository
) : ViewModel() {

    @Inject
    lateinit var fbRdb: FirebaseDatabase

    @Inject
    lateinit var fbStorage: FirebaseStorage

    @Inject
    lateinit var fbFireStore: FirebaseFirestore

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var _auth: FirebaseAuth? = null
    val auth: FirebaseAuth?
        get() = _auth

    private val practiceSong = mutableListOf<Song>().apply {
        add(Song("연습모드", "연습 1"))
        add(Song("연습모드", "연습 2"))
        add(Song("연습모드", "연습 3"))
        add(Song("연습모드", "연습 4"))
        add(Song("연습모드", "연습 5"))
    }

    private val _songList = MutableStateFlow(practiceSong)
    val songList: StateFlow<MutableList<Song>> = _songList.asStateFlow()

    fun getUserAuth(): FirebaseAuth? {
        _auth = Firebase.auth
        return auth
    }

    fun renewSongList(uid: String) {
        _isLoading.value = true
        fbRdb.getReference(uid).get().addOnSuccessListener {
            for (i in it.children) {
                if (songList.value.any() { it.filename == i.key.toString() }) {
                    continue
                } else {
                    songList.value.add(Song(uid, i.key.toString()))
                }
            }
            _isLoading.value = false
        }

    }

    fun putSong(song: Song, name: String, storage: StorageReference?, uid: String, uri: Uri?) {
        _isLoading.value = true
        viewModelScope.launch {
            runCatching {
                mainRepository.putSong(song)
            }.onSuccess {
                val map = mapOf(
                    "data" to ""
                )
                fbRdb.getReference(uid).child(name).setValue(map)
                storage?.putFile(uri!!)?.addOnSuccessListener {
                    renewSongList(uid)
                }
            }.onFailure {
                _isLoading.value = false
            }
        }
    }

}