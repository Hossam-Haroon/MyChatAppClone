package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.data.models.User
import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class SetUserUseCase @Inject constructor(private val repository: FireStoreRepository) {
    operator fun invoke(user: User, phoneNumber: String) {
        repository.setUser(user, phoneNumber)
    }
}