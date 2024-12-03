package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class GenerateChatIdUseCase @Inject constructor(private val repository: FireStoreRepository) {

    operator fun invoke(user1: String, user2: String):String{
        return repository.generateChatId(user1, user2)
    }
}