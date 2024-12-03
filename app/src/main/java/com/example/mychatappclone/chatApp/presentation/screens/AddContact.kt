package com.example.mychatappclone.chatApp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.mychatappclone.ui.theme.Blue40
import com.example.mychatappclone.ui.theme.Blue60

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun AddNewContact(navController: NavController){
    val viewModel : MyViewModel = hiltViewModel()

    //val userData = viewModel.getUserData(viewModel.userPhoneNumber())

   /* val receivedData = navController
        .previousBackStackEntry?.savedStateHandle?.get<User>("user_data")*/

    Scaffold (topBar = {
        Box (
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Blue40)){
            Row (
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(
                        id = R.drawable.arrow_back_icon ),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            navController.navigateUp()
                            // navigate back
                        }
                )
            }



        }
    }
    ){ _->
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
            ) {
            Box (
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 5.dp, top = 15.dp, end = 5.dp)
                    .border(1.dp, Blue40, shape = RoundedCornerShape(8.dp))
                    .clickable {
                       /* val gson = Gson()
                        val userJson = gson.toJson(userData)
                        Log.d("checkUserAgain","$userData")
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "user_data",
                            userJson
                        )*/
                        navController.navigate("addSingleContentScreen")
                        // navigate to add contact screen
                        // add new contact
                    }


            ){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "Add Contact",
                        fontSize = 15.sp,
                        color = Blue60,
                        modifier = Modifier.padding(start = 7.dp))
                }


            }
            Box (
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 5.dp, top = 15.dp, end = 5.dp)
                    .border(1.dp, Blue40, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        // add new group screen
                    }


            ){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "Add New Group",
                        fontSize = 15.sp,
                        color = Blue60,
                        modifier = Modifier.padding(start = 7.dp))
                }


            }
        }
    }

}