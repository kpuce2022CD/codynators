package com.example.features.main.profile.domain

import com.example.features.main.data.repository.FirebaseRepository
import javax.inject.Inject

class GetMySongUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke() =
        firebaseRepository.getFromRDB()
}