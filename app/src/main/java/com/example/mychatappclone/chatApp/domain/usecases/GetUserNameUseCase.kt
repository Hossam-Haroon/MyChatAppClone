package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import com.example.mychatappclone.core.domain.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserNameUseCase @Inject constructor(private val repository: FireStoreRepository) {

     operator fun invoke(): Flow<UiState<String>>{
        return repository.getUserName()
    }
}