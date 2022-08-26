package com.example.features.main.domain

import com.example.features.main.data.dto.Song
import com.example.features.main.data.repository.UpLoadRepositoryImpl
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val upLoadRepository: UpLoadRepositoryImpl
) {
    suspend operator fun invoke(song: Song) = upLoadRepository.delete(song)
}