package com.example.features.main.domain

import com.example.features.main.data.dto.Song
import com.example.features.main.data.repository.UpLoadRepositoryImpl
import javax.inject.Inject

class InsertUserCase @Inject constructor(
    private val upLoadRepository: UpLoadRepositoryImpl
) {
    suspend operator fun invoke(song: Song) = upLoadRepository.insertSong(song)
}