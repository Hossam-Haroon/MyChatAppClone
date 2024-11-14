package com.example.mychatappclone.userInterface.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.example.chattingapp.viewModel.MyViewModel
import com.example.mychatappclone.R
import com.example.mychatappclone.ui.theme.Blue20
import com.example.mychatappclone.ui.theme.Blue40
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch


@Composable
fun EnterPersonalDataScreen(number : String, navController: NavController) {


    val viewModel: MyViewModel = hiltViewModel()
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var name by remember {
        mutableStateOf("")
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri

        }
    val coroutineScope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .background(Blue20),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(70.dp)
                .height(70.dp),
        ) {
            if (imageUri != null){
                GlideImage(
                    imageModel = {
                        imageUri
                    },
                    modifier = Modifier
                        .fillMaxSize(),
                    previewPlaceholder = painterResource(id = R.drawable.defaultpfpsvg)

                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.defaultpfpsvg),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize())
            }

            /*Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(
                    id = R.drawable.defaultpfpsvg),
                contentDescription = null,
                contentScale = ContentScale.Crop


            )*/
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.camera_icon),
                    contentDescription = null
                )
            }

        }

        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(value = name, onValueChange = {
            name = it
        }, Modifier.background(Color.White))
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.addUserToFirebase(imageUri,name,number,navController)
                }


               /* val user = User(
                    name = name, phoneNumber = number, imageUrl = imageUri.toString()
                )
                viewModel.setUser(
                    user, number
                )
                navController
                    .currentBackStackEntry?.savedStateHandle?.set("user_data", user)
                navController.navigate("mainScreen")*/


            },
            colors = androidx.compose.material.ButtonDefaults.buttonColors(Blue40)
        ) {
            Text(text = "Save", fontSize = 15.sp)
        }
    }
}
