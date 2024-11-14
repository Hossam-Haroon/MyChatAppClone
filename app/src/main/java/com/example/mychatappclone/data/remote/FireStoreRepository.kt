package com.example.mychatappclone.data.remote

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.navigation.NavController
import com.example.chattingapp.data.models.ChatPreview
import com.example.chattingapp.data.models.Message
import com.example.chattingapp.data.models.User
import com.example.chattingapp.utils.UiState
import com.example.mychatappclone.data.models.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class FireStoreRepository @Inject constructor(val fireStore: FirebaseFirestore, val firebaseAuth: FirebaseAuth,
                                              val firebaseStorage: FirebaseStorage, private val context : Context
) {


    fun setUser(user: User, phoneNumber: String) {
        fireStore.collection("user")
            .document(phoneNumber)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("FirestoreUpload", "User document created/updated")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreUpload", "Error writing document", e)
            }
    }


    fun getUserData(phoneNumber: String, onUserDataReceived: (User?)-> Unit) {
        fireStore.collection("user")
            .document(phoneNumber)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)!!
                onUserDataReceived(user)
                Log.d("LoggedInUser", "data : $user")
            }
            .addOnFailureListener {
                Log.d("LoggedInUser", "data : something wrong")
            }

    }

    suspend fun addUserToFirebase(
        image : Uri?,
        name : String, phoneNumber : String,
        navController: NavController
    ){
        if (image != null) {
            val user = User(
                name = name, phoneNumber = phoneNumber, imageUrl = uploadImage(image,context)
            )
            setUser(
                user, phoneNumber
            )
            Log.d("checkUser","$user")
            navController.navigate("mainScreen")
        }else{
            val user = User(
                name = name, phoneNumber = phoneNumber
            )
            setUser(
                user, phoneNumber
            )
            Log.d("checkUser","$user")
            navController.navigate("mainScreen")
        }
    }


    private fun getFileExtension(context: Context, uri : Uri?): String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(uri?.let {
            context.contentResolver.getType(
                it
            )
        })
    }

    fun checkUserPhone(phone: String) {
        fireStore.collection("user")
            .whereEqualTo("phoneNumber", phone)
            .get()
            .addOnSuccessListener { document ->
                if (document.documents.size > 0) {
                    val user = document.documents[0].toObject(User::class.java)

                }


            }
    }

    fun logOut() {
        firebaseAuth.signOut()
    }


    fun userPhoneNumber(): String {
        if (firebaseAuth.currentUser?.phoneNumber != null) {
            return firebaseAuth.currentUser?.phoneNumber!!
        }
        return ""

    }

    private fun generateChatId(user1: String, user2: String): String {
        return if (user1 < user2) "${user1}-$user2" else "$user2-$user1"
    }

    fun createOneOnOneChat( secondUserNumber: String) {
        if (firebaseAuth.currentUser != null){
            val currentUserNumber = firebaseAuth.currentUser?.phoneNumber
            Log.d("yoyoyo", "data : $currentUserNumber")
            val chatId =
                generateChatId(currentUserNumber!!, secondUserNumber)
            val chat = Chat(
                chatId, listOf(currentUserNumber, secondUserNumber)
            )
            fireStore.collection("chat")
                .document(chatId)
                .set(chat)
        }


    }

    fun sendMessage(senderId: String, content: String, chatId: String) {
        val message = Message(
            messageId = UUID.randomUUID().toString(),
            chatId, content, senderId
        )
        fireStore.collection("message")
            .document(chatId)
            .collection("chat_messages")
            .document(message.messageId)
            .set(message)

        fireStore.collection("chat")
            .document(chatId)
            .update(
                mapOf(
                    "lastMessage" to content,
                    "lastMessageTime" to message.timeStamp,
                    "lastMessageSenderId" to senderId
                )
            )
    }

    fun fetchAllChats(
        currentPhoneNumber: String
    ): Flow<UiState<List<ChatPreview>>> {
        return callbackFlow {

            val listener = fireStore.collection("chat")
                .whereArrayContains("participants", currentPhoneNumber)
                .orderBy("lastMessageTime", Query.Direction.DESCENDING)
                .addSnapshotListener { snapShot, error ->
                    if (error != null){
                        trySend(UiState.Error(error.message!!))
                        return@addSnapshotListener
                    }
                    launch {
                        // val chatsList: MutableList<ChatPreview> = mutableListOf()
                        val chatsList = snapShot?.documents?.mapNotNull { doc ->
                            val chat = doc.toObject(Chat::class.java)
                            if (chat?.isGroup == false){
                                //oneOnOneChat
                                val otherUserPhone =
                                    chat.participants.find { it != currentPhoneNumber } ?: return@mapNotNull null
                                Log.d("checkNumber2", otherUserPhone)
                                val otherUserPhoneCleaned = otherUserPhone.trim()
                                Log.d("checkNumber", currentPhoneNumber)

                                val user = fireStore.collection("user")
                                    .document(otherUserPhoneCleaned)
                                    .get()
                                    .await()

                                val otherUserName = user.getString("name") ?: otherUserPhone
                                Log.d("checkName", otherUserName)
                                val otherUserPictureId = user.getString("imageUrl") ?: otherUserPhone


                                ChatPreview(
                                    chat.chatId,
                                    chat.lastMessage,
                                    otherUserPhone,
                                    otherUserName,
                                    chat.lastMessageTime,
                                    imageUrl = getTheUploadedImage(otherUserPictureId)
                                )
                            }else{
                                //GroupChat
                                val otherUserPhone =
                                    chat?.participants?.find { it != currentPhoneNumber } ?: return@mapNotNull null


                                val user = fireStore.collection("user")
                                    .document(otherUserPhone.trim())
                                    .get()
                                    .await()

                                val otherUserName = user.getString("name") ?: otherUserPhone

                                ChatPreview(
                                    chat.chatId,
                                    chat.lastMessage,
                                    otherUserPhone,
                                    otherUserName,
                                    chat.lastMessageTime
                                )
                            }



                        } ?: emptyList()
                        trySend(UiState.Success(chatsList))
                    }

                }
            awaitClose {
                listener.remove()
            }
        }

    }
    suspend fun checkChatExistence(secondPhoneNumber:String): Boolean? {
           val exists =
               firebaseAuth.currentUser?.phoneNumber?.let { generateChatId(it,secondPhoneNumber) }?.let {
                   fireStore.collection("chat")
                       .document(it)
                       .get()
                       .await()
                       .exists()
               }
        return exists

    }
    suspend fun checkUserExistence(userNumber : String): Boolean {
        val exists =
                fireStore.collection("user")
                    .document(userNumber)
                    .get()
                    .await()
                    .exists()

        return exists
    }

    fun getUserName():Flow<UiState<String>>{
        return callbackFlow {
            val listener = fireStore.collection("user")
                .document(userPhoneNumber())
                .get()
                .addOnSuccessListener { document->
                    val userName: String
                    val user = document.toObject(User::class.java)
                    userName = user!!.name
                    trySend(UiState.Success(userName))
                    close()
                }
                .addOnFailureListener {error->
                    trySend(UiState.Error(error.message ?: "unknown error"))
                    close()

                }
            awaitClose {

            }
        }
    }

    fun updateUserName(name : String){
        fireStore.collection("user")
            .document(userPhoneNumber())
            .update(
                mapOf(
                    "name" to name
                )
            )

    }

    fun addFriendNameToMyData(currentPhoneNumber: String, otherUserName: String){
        val friendsNameList: MutableList<String> = mutableListOf()
        friendsNameList.add(otherUserName)

        fireStore.collection("user")
            .document(currentPhoneNumber)
            .update(
                "friendsName", FieldValue.arrayUnion(otherUserName)
            )
            .addOnSuccessListener {
                Log.d("AddFriend", " Friend added successfully")
            }
            .addOnFailureListener {
                Log.d("AddFriend", " something wrong happened")
            }
    }

    suspend fun uploadImage(imageUri : Uri, context : Context):String? = withContext(Dispatchers.IO){
        try{
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val bytes = inputStream?.readBytes()
            Log.d("ImageUpload", "Bytes read: ${bytes?.size}")

            val fileName = "IMAGE_DATA"+System.currentTimeMillis()+"."+getFileExtension(context,imageUri)
            val parseFile = ParseFile(fileName,bytes)

            if (bytes == null){
                return@withContext null
            }

            //parseFile.save()
            /*parseFile.saveInBackground(
                { Log.d("Back4App", "File uploaded successfully") },
                { error -> Log.e("Back4App", "File upload failed: $error") }
            )*/
            var retries = 5
            while (retries > 0) {
                try {
                    parseFile.save()
                    Log.d("Back4App", "File uploaded successfully")
                    break // Exit loop on success
                } catch (e: Exception) {
                    retries--
                    Log.e("Back4App", "File upload failed: ${e.message} - Retries left: $retries")
                    if (retries == 0) return@withContext null
                    delay(1000) // Delay before retrying
                }
            }

            val mediaItem = ParseObject("ProfilePictures")
            mediaItem.put("file",parseFile)
            mediaItem.put("type","image")
            mediaItem.save()
            Log.d("checkObjectId", mediaItem.objectId)
            return@withContext mediaItem.objectId
        }catch (e:Exception){
            e.printStackTrace()
            return@withContext null
        }
    }

    private suspend fun getTheUploadedImage(userImageId: String): String? = withContext(Dispatchers.IO){
        try{
            val query = ParseQuery.getQuery<ParseObject>("ProfilePictures")
            val result = query.get(userImageId)

           val url = (result.get("file") as ParseFile).url

            return@withContext url
        }catch (e : Exception){
            e.printStackTrace()
            return@withContext null
        }
    }

    fun getUserImage(): Flow<UiState<String?>>{
        return flow {
            try {
                val user = fireStore.collection("user")
                    .document(userPhoneNumber())
                    .get()
                    .await()

                val currentUserPicture = user.getString("imageUrl")


                emit(UiState.Success(currentUserPicture?.let { getTheUploadedImage(it) }))
                if (currentUserPicture != null) {
                    Log.d("checkIdPicture", currentUserPicture)
                }
            }catch (e:Exception){
                emit(UiState.Error(e.message ?: "An unknown error occurred"))
            }


        }
    }

    suspend fun updateUserImage(uri: Uri?){
        uri?.let {
            val imageUrl = uploadImage(uri,context)
            if (imageUrl != null){
                fireStore.collection("user")
                    .document(userPhoneNumber())
                    .update(
                        mapOf(
                            "imageUrl" to imageUrl
                        )
                    )
                    .addOnSuccessListener { Log.d("FirestoreUpdate", "Image URL updated successfully") }
                    .addOnFailureListener { Log.e("FirestoreUpdate", "Failed to update image URL") }
            }else{
                Log.e("ImageUpload", "Failed to upload image to Back4App")
            }
        }

    }

}