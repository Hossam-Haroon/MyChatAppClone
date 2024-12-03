package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: FireStoreRepository) {
    operator fun invoke() {
        repository.logOut()
    }
}