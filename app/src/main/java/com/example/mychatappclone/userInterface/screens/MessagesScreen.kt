package com.example.mychatappclone.userInterface.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.example.chattingapp.data.models.ChatPreview

import com.example.chattingapp.utils.UiState
import com.example.chattingapp.viewModel.MyViewModel
import com.example.mychatappclone.R
import com.example.mychatappclone.ui.theme.Blue20
import com.example.mychatappclone.ui.theme.Gray20
import com.example.mychatappclone.ui.theme.Gray40
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MessagesScreen(navController: NavController){

    val viewModel : MyViewModel = hiltViewModel()
    val state by viewModel.chatPreviewList.collectAsState()

    /*val receivedUserData = navController
        .currentBackStackEntry?.savedStateHandle?.get<User>("user_data")*/

    LaunchedEffect(Unit) {
        viewModel.fetchAllChatsPreview(viewModel.userPhoneNumber())
    }

    Box (modifier = Modifier
        .fillMaxSize()
        .padding(top = 73.dp)){
        Image(
            painter = painterResource(id = R.drawable.backgroundimage),
            contentDescription = null,
            modifier = Modifier.fillMaxSize())
        LazyColumn (verticalArrangement = Arrangement.Top){
            item {

                Row (
                    Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                    , horizontalArrangement = Arrangement.Start){
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(Gray40),
                        modifier = Modifier.padding(start = 5.dp, end = 7.dp)
                    ){
                        Text(text = "All", fontSize = 15.sp, color = Color.White)
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(Gray40),
                        modifier = Modifier.padding(end = 7.dp)
                    ){
                        Text(text = "Favourite", fontSize = 15.sp, color = Color.White)
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(Gray40),
                        modifier = Modifier.padding(end = 7.dp)
                    ){
                        Text(text = "Groups", fontSize = 15.sp, color = Color.White)
                    }
                }
            }
            when(val localState = state){
                is UiState.Error -> {
                }
                UiState.Loading -> {}
                is UiState.Success -> {
                    items(localState.data){chat ->
                        ChatLookItem(chat)
                    }
                }
                else -> {}
            }
        }
    }


}


@Composable
fun ChatLookItem(
    chatPreview: ChatPreview
    // parameters to pass the data to replace every default value
){
    Spacer(modifier = Modifier.height(2.dp))
    Row (
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Blue20),
        verticalAlignment = Alignment.CenterVertically){

        /*GlideImage(model = chatPreview.imageUrl, contentDescription = null,
            modifier = Modifier
                .width(45.dp)
                .height(40.dp)
                .padding(start = 5.dp),
            contentScale = ContentScale.Crop)*/

        GlideImage(imageModel = { chatPreview.imageUrl },
            modifier = Modifier
                .width(45.dp)
                .height(40.dp)
                .padding(start = 5.dp),
            previewPlaceholder = painterResource(id = R.drawable.defaultpfpsvg)
            )

       /* Image(modifier = Modifier
            .width(45.dp)
            .height(40.dp)
            .padding(start = 5.dp),
            painter = painterResource(id = R.drawable.defaultpfpsvg),
            contentDescription = null,
            contentScale = ContentScale.Crop)*/
        Column (
            Modifier
                .fillMaxWidth()
                .padding(start = 8.dp), verticalArrangement = Arrangement.SpaceAround){
            Row (
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text(text = chatPreview.otherPersonName, fontSize = 15.sp)
                Text(text = chatPreview.lastMessageTime.toString(), fontSize = 9.sp, modifier = Modifier.padding(end = 6.dp))
                //Text(text = String.format("%02d:%02d", chatPreview.lastMessageTime/60,chatPreview.lastMessageTime%60), fontSize = 9.sp, modifier = Modifier.padding(end = 6.dp))
            }
            Text(text = chatPreview.lastMessage, fontSize = 11.sp, color = Gray20)
        }




    }
}


@Composable

fun NewsScreen(){
    Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){




    }

}
@Composable
fun SocietiesScreen(){
    Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "hello SocietiesScreen", fontSize = 20.sp)
        Text(text = "hello hossam", fontSize = 20.sp)



    }

}
@Composable
fun CallsScreen(){
    Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "hello calls", fontSize = 20.sp)
        Text(text = "hello hossam", fontSize = 20.sp)



    }

}
