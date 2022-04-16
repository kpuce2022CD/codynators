package com.example.codrum.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class MainViewModel : ViewModel() {
    private final val GALLERY_CODE = 10
    val userUID = Firebase.auth.currentUser?.uid.toString()
    val storage = FirebaseStorage.getInstance()
    var storageRef = storage.getReference()


}