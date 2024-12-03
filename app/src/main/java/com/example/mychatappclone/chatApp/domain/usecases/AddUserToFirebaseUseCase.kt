package com.example.mychatappclone.chatApp.domain.usecases

import android.net.Uri
import androidx.navigation.NavController
import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
import javax.inject.Inject

class AddUserToFirebaseUseCase @Inject constructor(private val repository: FireStoreRepository) {

    suspend operator fun invoke(
        image : Uri?,
        name : String, phoneNumber : String,
        navController: NavController
    ){
        repository.addUserToFirebase(image,name,phoneNumber, navController)
    }
}