package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.data.models.User
import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val repository: FireStoreRepository) {
    operator fun invoke(phoneNumber: String, onUserDataReceived: (User?) -> Unit) {
        repository.getUserData(phoneNumber, onUserDataReceived)
    }
}