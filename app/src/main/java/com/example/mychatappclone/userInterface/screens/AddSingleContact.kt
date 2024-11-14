package com.example.mychatappclone.userInterface.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.example.chattingapp.data.models.User

import com.example.chattingapp.viewModel.MyViewModel
import com.example.mychatappclone.R
import com.example.mychatappclone.ui.theme.Blue40
import com.example.mychatappclone.ui.theme.Gray40
import com.google.gson.Gson
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddSingleContactScreen(navController: NavController){
    var firstName by remember{
        mutableStateOf("")
    }
    var familyName by remember{
        mutableStateOf("")
    }
    var phoneNumber by remember{
        mutableStateOf("")
    }
    var countryCode by remember{
        mutableStateOf("+20")
    }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewModel : MyViewModel  = hiltViewModel()
    val gson = Gson()
    val receivedUserJson = navController
        .previousBackStackEntry?.savedStateHandle?.get<String>("user_data")
    val user = receivedUserJson?.let { gson.fromJson(it,User::class.java) }


    Scaffold (topBar = {
        Box (
            Modifier
                .height(70.dp)
                .fillMaxWidth()
                .background(Blue40)){
            Row (
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Icon(
                    painter = painterResource(
                        id = R.drawable.arrow_back_icon ),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            //navController.navigateUp()
                            // navigate back
                        }
                )
                Text(
                    text = "Add Contact",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 9.dp),
                    color = Color.White
                )
            }

        }
    }){ _->

        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Row (verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()){
                Icon(
                    painter = painterResource(id = R.drawable.person_icon),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 5.dp)
                )
                OutlinedTextField(
                    value = firstName, onValueChange = {firstName = it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    placeholder = {
                        Text(text = "first Name", color = Gray40)
                    }
                )
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                OutlinedTextField(value = familyName, onValueChange = {familyName = it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 34.dp, end = 5.dp),
                    placeholder = {
                        Text(text = "family Name", color = Gray40)
                    }
                )
            }

            Row (modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){
                Icon(
                    painter = painterResource(id = R.drawable.call_icon),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 5.dp, start = 5.dp)
                )
                CountryCodePicker(selectedCountry = countryCode) {
                    countryCode = it
                }
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {phoneNumber = it},
                    modifier = Modifier.padding(5.dp),
                    placeholder = {
                        Text(text = "Enter Phone Number", color = Gray40)
                    }
                )

            }
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {

                        //viewModel.createOneOnOneChat(user.phoneNumber,phoneNumber)
                    /*if (user != null) {
                        viewModel.getUserData(user.phoneNumber){user->
                            viewModel.createOneOnOneChat(
                                user!!.phoneNumber,phoneNumber
                            )
                        }
                    }*/
                    coroutineScope.launch {
                        if (viewModel.checkChatExistence("${countryCode}${phoneNumber.toInt()}") == true){
                            Toast.makeText(context,"This Number Already Exist",Toast.LENGTH_LONG).show()
                        }else{
                            viewModel.createOneOnOneChat("${countryCode}${phoneNumber.toInt()}")
                            navController.popBackStack("mainScreen", inclusive = false)
                        }
                    }



                    //navController.navigate("mainScreen")
                    // enter the number
                    // make chat between you and the guy
                    // save contact
                    // navigate to main screen
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Blue40),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)) {

                Text(text = "Save", fontSize = 15.sp, color = Color.White)
            }
        }

    }

}