package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: FireStoreRepository) {
    operator fun invoke(senderId: String, content: String, chatId: String) {
        repository.sendMessage(senderId, content, chatId)
    }
}