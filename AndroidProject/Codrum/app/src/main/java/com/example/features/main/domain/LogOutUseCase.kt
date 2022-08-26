package com.example.features.main.domain

import com.example.features.main.data.repository.FirebaseRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke() = firebaseRepository.signOut()
}