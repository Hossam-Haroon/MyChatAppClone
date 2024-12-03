package com.example.mychatappclone.chatApp.domain.usecases

import android.net.Uri
import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class UpdateUserImageUseCase @Inject constructor(private val repository: FireStoreRepository) {
    suspend operator fun invoke(uri: Uri?) {
        repository.updateUserImage(uri)
    }
}