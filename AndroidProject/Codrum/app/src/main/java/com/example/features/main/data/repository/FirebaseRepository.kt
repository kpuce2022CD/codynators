package com.example.features.main.data.repository

import android.net.Uri
import com.example.features.main.data.dto.Song
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    suspend fun uploadOnFirebase(title: String, file: String, uri: Uri)

    suspend fun deleteOnFirebase(title: String, file: String)

    fun getFromRDB(): Flow<List<Song>>

    suspend fun signOut()
}