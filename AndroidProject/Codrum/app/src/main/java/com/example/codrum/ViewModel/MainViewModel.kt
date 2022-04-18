package com.example.codrum.ViewModel

import androidx.lifecycle.ViewModel
import com.example.codrum.Model.Music
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {

    private val fbStore = FirebaseFirestore.getInstance()
    private val fbRdb = Firebase.database
    val musicItem = mutableListOf<Music>()

    fun getUserName(userUid: String): String{
        var name = ""
        fbStore.collection("User")
            .document(userUid)
            .get()
            .addOnSuccessListener {
                name = it["name"].toString()
            }
        return name
    }

    fun getSongList(userUid : String){
        fbRdb.getReference(userUid).get().addOnSuccessListener {
            it.key
        }
    }
}