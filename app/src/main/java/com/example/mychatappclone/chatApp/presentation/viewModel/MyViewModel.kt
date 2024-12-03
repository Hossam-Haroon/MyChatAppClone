package com.example.chattingapp.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mychatappclone.chatApp.presentation.model.ChatPreview
import com.example.mychatappclone.chatApp.data.models.User

import com.example.mychatappclone.core.domain.UiState
import com.example.mychatappclone.chatApp.domain.usecases.AddUserToFirebaseUseCase
import com.example.mychatappclone.chatApp.domain.usecases.CheckChatExistenceUseCase
import com.example.mychatappclone.chatApp.domain.usecases.CheckUserExistenceUseCase
import com.example.mychatappclone.chatApp.domain.usecases.CreateOneOnOneChatUseCase
import com.example.mychatappclone.chatApp.domain.usecases.FetchAllChatsUseCase
import com.example.mychatappclone.chatApp.domain.usecases.GetUserDataUseCase
import com.example.mychatappclone.chatApp.domain.usecases.GetUserImageUseCase
import com.example.mychatappclone.chatApp.domain.usecases.GetUserNameUseCase
import com.example.mychatappclone.chatApp.domain.usecases.LogOutUseCase
import com.example.mychatappclone.chatApp.domain.usecases.SetUserUseCase
import com.example.mychatappclone.chatApp.domain.usecases.UpdateUserImageUseCase
import com.example.mychatappclone.chatApp.domain.usecases.UpdateUserNameUseCase
import com.example.mychatappclone.chatApp.domain.usecases.UploadImageUseCase
import com.example.mychatappclone.chatApp.domain.usecases.UserPhoneNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val createOneOnOneChatUseCase: CreateOneOnOneChatUseCase,
    private val fetchAllChatsUseCase: FetchAllChatsUseCase,
    private val updateUserImageUseCase: UpdateUserImageUseCase,
    private val getUserImageUseCase: GetUserImageUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val checkChatExistenceUseCase: CheckChatExistenceUseCase,
    private val checkUserExistenceUseCase: CheckUserExistenceUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val userPhoneNumberUseCase: UserPhoneNumberUseCase,
    private val setUserUseCase: SetUserUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val addUserToFirebaseUseCase: AddUserToFirebaseUseCase,
    private val getUserNameUseCase: GetUserNameUseCase
): ViewModel() {

    private var _chatPreviewList : MutableStateFlow<UiState<List<ChatPreview>>> = MutableStateFlow(
        UiState.Loading)
   val  chatPreviewList = _chatPreviewList.asStateFlow()

    private var _userName : MutableStateFlow<UiState<String>> = MutableStateFlow(UiState.Loading)
     val userName = _userName.asStateFlow()

    private var _userPicture : MutableStateFlow<UiState<String?>> = MutableStateFlow(UiState.Loading)
    val userPicture = _userPicture.asStateFlow()


    fun setUser(user : User, phoneNumber : String){
        setUserUseCase(user, phoneNumber)
    }

    suspend fun addUserToFirebase(image : Uri?, name : String, phoneNumber : String, navController: NavController){
        addUserToFirebaseUseCase(image,name,phoneNumber,navController)
    }

    fun getUserData(phoneNumber:String, onUserDataReceived:(User?)-> Unit){
         getUserDataUseCase(phoneNumber = phoneNumber){
            onUserDataReceived(it)
        }
    }


    fun userPhoneNumber(): String{
        return userPhoneNumberUseCase()
    }

    suspend fun checkChatExistence(secondPhoneNumber:String): Boolean? {
       return checkChatExistenceUseCase(secondPhoneNumber)
    }
    suspend fun checkUserExistence(userPhoneNumber : String): Boolean{
        Log.d("checkExist", "check :${checkUserExistenceUseCase(userPhoneNumber)}")
        return checkUserExistenceUseCase(userPhoneNumber)
    }

    fun signOut(){
        logOutUseCase()
    }

    fun getUserName(){
        viewModelScope.launch {
            getUserNameUseCase().collect{uiState->
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
        updateUserNameUseCase(name)
    }

    fun uploadImage(image: Uri?,context : Context){
        viewModelScope.launch {
            if (image != null) {
                uploadImageUseCase(image,context)
            }
        }

    }

    fun getUserImage(){
        viewModelScope.launch {
            getUserImageUseCase().collect{uiState->
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
            updateUserImageUseCase(uri)
        }

    }

    fun fetchAllChatsPreview(yourPhoneNumber : String){
        viewModelScope.launch {
            fetchAllChatsUseCase(yourPhoneNumber).collect{uiState->
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
        createOneOnOneChatUseCase(secondNumber)
    }
}