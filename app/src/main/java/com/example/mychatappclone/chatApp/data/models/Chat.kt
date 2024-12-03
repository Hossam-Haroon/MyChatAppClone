package com.example.mychatappclone.chatApp.data.models

data class Chat(
    val chatId : String = "",
    val participants : List<String> = listOf(),
    val lastMessage : String = "",
    val lastMessageTime : Long = System.currentTimeMillis(),
    val lastMessageSenderId : String = "",
    val isGroup : Boolean = false,
    val groupName : String? = null,
    val groupImage : String? = null
)