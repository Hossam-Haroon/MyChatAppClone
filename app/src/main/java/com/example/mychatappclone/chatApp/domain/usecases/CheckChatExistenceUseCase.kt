package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class CheckChatExistenceUseCase @Inject constructor(private val repository : FireStoreRepository) {
    suspend operator fun invoke(secondPhoneNumber: String): Boolean? {
        return repository.checkChatExistence(secondPhoneNumber)
    }
}