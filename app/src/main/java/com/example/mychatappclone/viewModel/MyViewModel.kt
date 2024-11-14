package com.example.chattingapp.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.chattingapp.data.models.ChatPreview
import com.example.chattingapp.data.models.User

import com.example.chattingapp.utils.UiState
import com.example.mychatappclone.data.remote.FireStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val fireStoreRepository: FireStoreRepository): ViewModel() {

    private var _chatPreviewList : MutableStateFlow<UiState<List<ChatPreview>>> = MutableStateFlow(UiState.Loading)
   val  chatPreviewList = _chatPreviewList.asStateFlow()

    private var _userName : MutableStateFlow<UiState<String>> = MutableStateFlow(UiState.Loading)
     val userName = _userName.asStateFlow()

    private var _userPicture : MutableStateFlow<UiState<String?>> = MutableStateFlow(UiState.Loading)
    val userPicture = _userPicture.asStateFlow()


    fun setUser(user : User, phoneNumber : String){
        fireStoreRepository.setUser(user, phoneNumber)
    }

    suspend fun addUserToFirebase(image : Uri?, name : String, phoneNumber : String, navController: NavController){
        fireStoreRepository.addUserToFirebase(image,name,phoneNumber,navController)
    }
    fun getUserData(phoneNumber:String, onUserDataReceived:(User?)-> Unit){

         fireStoreRepository.getUserData(phoneNumber = phoneNumber){
            onUserDataReceived(it)
        }
    }


    fun userPhoneNumber(): String{
        return fireStoreRepository.userPhoneNumber()
    }

    suspend fun checkChatExistence(secondPhoneNumber:String): Boolean? {
       return fireStoreRepository.checkChatExistence(secondPhoneNumber)
    }
    suspend fun checkUserExistence(userPhoneNumber : String): Boolean{
        Log.d("checkExist", "check :${fireStoreRepository.checkUserExistence(userPhoneNumber)}")
        return fireStoreRepository.checkUserExistence(userPhoneNumber)
    }

    fun signOut(){
        fireStoreRepository.logOut()
    }

    fun getUserName(){
        viewModelScope.launch {
            fireStoreRepository.getUserName().collect{uiState->
                when(uiState){
                    is UiState.Error -> {_userName.value = uiState}
                    UiState.Loading -> {}
                    is UiState.Success -> {
                        _userName.value = uiState
                    }
                }

            }
        }
    }

    fun updateUserName(name: String){
        fireStoreRepository.updateUserName(name)
    }

    fun uploadImage(image: Uri?,context : Context){
        viewModelScope.launch {
            if (image != null) {
                fireStoreRepository.uploadImage(image,context)
            }
        }

    }

    fun getUserImage(){
        viewModelScope.launch {
            fireStoreRepository.getUserImage().collect{uiState->
                when(uiState){
                    is UiState.Error -> {}
                    UiState.Loading -> {}
                    is UiState.Success -> {
                        _userPicture.value = uiState
                    }
                }
            }
        }
    }

    fun updateUserImage(uri : Uri?){
        viewModelScope.launch {
            fireStoreRepository.updateUserImage(uri)
        }

    }

    fun fetchAllChatsPreview(yourPhoneNumber : String){
        viewModelScope.launch {
            fireStoreRepository.fetchAllChats(yourPhoneNumber).collect{uiState->
                when(uiState){
                    is UiState.Error -> {}
                    UiState.Loading -> {}
                    is UiState.Success -> {
                        _chatPreviewList.value = uiState
                    }
                }

            }
        }
    }

    fun createOneOnOneChat( secondNumber : String){
        fireStoreRepository.createOneOnOneChat(secondNumber)
    }
}