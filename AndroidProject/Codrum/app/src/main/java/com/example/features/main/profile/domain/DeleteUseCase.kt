package com.example.features.main.profile.domain

import com.example.features.main.data.repository.FirebaseRepository
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(title: String, file: String) =
        firebaseRepository.deleteOnFirebase(title, file)
}

