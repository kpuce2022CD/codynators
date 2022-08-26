package com.example.features.main.domain

import com.example.features.main.data.dto.Song
import com.example.features.main.data.repository.UpLoadRepositoryImpl
import javax.inject.Inject

class UploadUseCase @Inject constructor(
    private val upLoadRepository: UpLoadRepositoryImpl
) {
    suspend operator fun invoke(song: Song) = upLoadRepository.upLoadSong(song)
}