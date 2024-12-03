package com.example.mychatappclone.chatApp.data.models

data class Message(
    val messageId : String = "",
    val chatId : String = "",
    val message : String="",
    val senderId : String="",
    val timeStamp : Long = System.currentTimeMillis(),
    val mediaUrl : String? = null
)
