package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class CheckUserExistenceUseCase @Inject constructor(private val repository: FireStoreRepository) {
    suspend operator fun invoke(userNumber: String): Boolean {
        return repository.checkUserExistence(userNumber)
    }
}