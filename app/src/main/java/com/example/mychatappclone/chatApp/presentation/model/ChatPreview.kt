package com.example.mychatappclone.chatApp.presentation.model


data class ChatPreview(
    val chatId :String,
    val lastMessage : String,
    val otherPersonNumber : String,
    val otherPersonName : String,
    val lastMessageTime : Long,
    val unreadMessages : Int = 0,
    val imageUrl : String? = null
)
