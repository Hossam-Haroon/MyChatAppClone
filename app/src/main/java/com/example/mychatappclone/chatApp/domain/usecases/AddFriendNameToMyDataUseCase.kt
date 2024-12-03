package com.example.mychatappclone.chatApp.domain.usecases

import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class AddFriendNameToMyDataUseCase @Inject constructor(val repository: FireStoreRepository) {

    operator fun invoke(currentPhoneNumber: String, otherUserName: String){
        repository.addFriendNameToMyData(currentPhoneNumber,otherUserName)
    }
}