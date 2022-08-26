package com.example.features.main.upload.domain

import android.net.Uri
import com.example.features.main.data.dto.Song
import com.example.features.main.data.dto.toFile
import com.example.features.main.data.repository.FirebaseRepository
import com.example.features.main.data.repository.UpLoadRepository
import javax.inject.Inject

class UploadUseCase @Inject constructor(
    private val upLoadRepository: UpLoadRepository,
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(song: Song, uri: Uri) {
        runCatching {
            upLoadRepository.upLoadSong(song)
        }.onSuccess {
            firebaseRepository.uploadOnFirebase(song.filename, song.toFile(), uri)
        }.onFailure {
            throw it
        }
    }
}