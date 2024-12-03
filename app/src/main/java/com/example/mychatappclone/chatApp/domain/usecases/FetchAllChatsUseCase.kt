package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import com.example.mychatappclone.chatApp.presentation.model.ChatPreview
import com.example.mychatappclone.core.domain.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAllChatsUseCase @Inject constructor(private val repository: FireStoreRepository) {
    operator fun invoke(currentPhoneNumber: String): Flow<UiState<List<ChatPreview>>> {
        return repository.fetchAllChats(currentPhoneNumber)
    }
}