package com.example.features.main.home.domain

import com.example.features.main.data.repository.UpLoadRepository
import javax.inject.Inject

class GetPracticeUseCase @Inject constructor(
    private val upLoadRepository: UpLoadRepository,
) {
    suspend operator fun invoke() = upLoadRepository.getPractice()
}