package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class GetTheUploadedImageUseCase @Inject constructor(private val repository: FireStoreRepository) {
    suspend operator fun invoke(userImageId: String) : String?{
        return repository.getTheUploadedImage(userImageId)
    }
}