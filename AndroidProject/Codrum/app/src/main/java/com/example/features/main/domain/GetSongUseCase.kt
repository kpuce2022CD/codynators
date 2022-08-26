package com.example.features.main.domain

import com.example.features.main.data.repository.UpLoadRepository
import javax.inject.Inject

class GetSongUseCase @Inject constructor(
    private val upLoadRepository: UpLoadRepository,
) {
    suspend operator fun invoke() = upLoadRepository.getPractice()
}