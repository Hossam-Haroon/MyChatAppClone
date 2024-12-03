package com.example.mychatappclone.chatApp.domain.usecases

import android.content.Context
import android.net.Uri
import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val repository: FireStoreRepository) {
    suspend operator fun invoke(imageUri: Uri, context: Context): String? {
        return repository.uploadImage(imageUri, context)
    }
}