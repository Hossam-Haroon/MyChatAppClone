package com.example.mychatappclone.chatApp.domain.firestoreRepository

import android.content.Context
import android.net.Uri
import androidx.navigation.NavController
import com.example.mychatappclone.chatApp.data.models.User
import com.example.mychatappclone.chatApp.presentation.model.ChatPreview
import com.example.mychatappclone.core.domain.UiState
import kotlinx.coroutines.flow.Flow

interface FireStoreRepository {

    fun setUser(user: User, phoneNumber: String)
    fun getUserData(phoneNumber: String, onUserDataReceived: (User?)-> Unit)
    suspend fun addUserToFirebase(
        image : Uri?,
        name : String, phoneNumber : String,
        navController: NavController
    )
    fun getFileExtension(context: Context, uri : Uri?): String?
    fun checkUserPhone(phone: String)
    fun logOut()
    fun userPhoneNumber(): String
    fun generateChatId(user1: String, user2: String): String
    fun createOneOnOneChat( secondUserNumber: String)
    fun sendMessage(senderId: String, content: String, chatId: String)
    fun fetchAllChats(
        currentPhoneNumber: String
    ): Flow<UiState<List<ChatPreview>>>
    suspend fun checkChatExistence(secondPhoneNumber:String): Boolean?
    suspend fun checkUserExistence(userNumber : String): Boolean
    fun getUserName():Flow<UiState<String>>
    fun updateUserName(name : String)
    fun addFriendNameToMyData(currentPhoneNumber: String, otherUserName: String)
    suspend fun uploadImage(imageUri : Uri, context : Context):String?
    suspend fun getTheUploadedImage(userImageId: String): String?
    fun getUserImage(): Flow<UiState<String?>>
    suspend fun updateUserImage(uri: Uri?)

}