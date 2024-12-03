package com.example.mychatappclone.chatApp.data.models

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val name : String = "",
    val phoneNumber: String = "",
    val friendsName : List<String> = listOf(),
    val imageUrl : String? = null,
    val fcmToken: String? = null
)
