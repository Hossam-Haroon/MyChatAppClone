package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class UpdateUserNameUseCase @Inject constructor(private val repository: FireStoreRepository) {
    operator fun invoke(name: String) {
        repository.updateUserName(name)
    }
}