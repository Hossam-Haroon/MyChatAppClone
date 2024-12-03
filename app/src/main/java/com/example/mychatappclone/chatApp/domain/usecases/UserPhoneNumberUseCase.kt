package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class UserPhoneNumberUseCase @Inject constructor(private val repository: FireStoreRepository) {

    operator fun invoke():String{
        return repository.userPhoneNumber()
    }
}