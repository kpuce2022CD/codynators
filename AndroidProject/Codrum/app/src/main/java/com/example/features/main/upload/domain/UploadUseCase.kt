package com.example.features.main.upload.domain

import android.net.Uri
import com.example.features.main.data.dto.Song
import com.example.features.main.data.dto.toFile
import com.example.features.main.data.repository.FirebaseRepository
import com.example.features.main.data.repository.UpLoadRepository
import javax.inject.Inject

/* 서버 연결확인하고
   성공블록으로 파이어베이스 업로드 이동합시다
*/
class UploadUseCase @Inject constructor(
    private val upLoadRepository: UpLoadRepository,
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(song: Song, uri: Uri) {
        runCatching {
            firebaseRepository.uploadOnFirebase(song.filename, song.toFile(), uri)
        }.onSuccess {
            upLoadRepository.upLoadSong(song)
        }.onFailure {
            throw it
        }
    }
}