package com.example.features.main.domain

import com.example.features.main.data.repository.UpLoadRepositoryImpl
import javax.inject.Inject

class GetSongUseCase @Inject constructor(
    private val upLoadRepository: UpLoadRepositoryImpl
) {
    suspend operator fun invoke() = upLoadRepository.getPractice()
}