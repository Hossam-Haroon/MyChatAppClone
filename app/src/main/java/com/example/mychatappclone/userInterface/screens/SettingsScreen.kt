package com.example.mychatappclone.userInterface.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chattingapp.utils.UiState

import com.example.chattingapp.viewModel.MyViewModel
import com.example.mychatappclone.R
import com.example.mychatappclone.ui.theme.Blue40
import com.example.mychatappclone.ui.theme.Blue60
import com.example.mychatappclone.ui.theme.Gray40
import com.skydoves.landscapist.glide.GlideImage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController){
    val viewModel : MyViewModel = hiltViewModel()
    val userName by viewModel.userName.collectAsState()
    val userPicture by viewModel.userPicture.collectAsState()

   val context = LocalContext.current
    var dismissDialog by remember {
        mutableStateOf(false)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            viewModel.updateUserImage(uri)
            viewModel.getUserImage()
            // here save the new image to back4app and save its id to firebase

        }
    val imageUri = when (val localState = userPicture) {
        is UiState.Success -> localState.data?.toUri()
        else -> null // Show placeholder if loading or error state
    }

    LaunchedEffect(Unit) {
        viewModel.getUserName()
        viewModel.getUserImage()
    }
    // collect the image as a state for update
    // when you click you choose an image from studio
    // get the name from the firestore
    // store it as an initial value here

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
                            navController.navigateUp()
                            // navigate back
                        }
                )
                Text(
                    text = "Settings",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 9.dp),
                    color = Color.White
                )
            }

        }
    }){ _ ->
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(20.dp))
            Box (Modifier.size(100.dp)){
                // here we need to pass the image of the user
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

                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.camera_icon),
                        contentDescription = null,
                        tint = Blue40,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                launcher.launch("image/*")
                                // change the picture from studio
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row (
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        dismissDialog = true

                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        painter = painterResource(id = R.drawable.person_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 5.dp))
                    Column (Modifier.padding(start= 15.dp)){
                        Text(text = "name", fontSize = 12.sp, color = Gray40)
                        // pass the value of the name to the text beneath
                        when(val localState = userName){
                            is UiState.Error -> {
                                Text(text = "Error :Loading Picture", fontSize = 20.sp)
                            }
                            UiState.Loading -> {}
                            is UiState.Success -> {
                                Text(text = localState.data, fontSize = 20.sp)
                                CustomDialog(
                                    dismissDialog = dismissDialog,
                                    onDismissRequest = { dismissDialog = false  },
                                    onConfirm = {enteredName->
                                        if (enteredName.isNotEmpty()){
                                            // do the changes here
                                            // update the firebase with the new value
                                            viewModel.updateUserName(enteredName)
                                            dismissDialog = false
                                        }else{
                                            Toast.makeText(context,"Enter a name please", Toast.LENGTH_LONG).show()
                                        }
                                    },
                                    passedName = localState.data     /* pass the name from the firestore */
                                )
                            }
                        }

                    }
                }

                Icon(
                    painter = painterResource(id = R.drawable.edit_name_icon),
                    contentDescription = null,
                    tint = Blue40,
                     modifier = Modifier.padding(end = 20.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .width(350.dp)
                    .fillMaxWidth()
                    .background(Blue60)

            )
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .clickable {
                        viewModel.signOut()
                        navController.navigateUp()
                        navController.navigate("login")
                    }
            ){
                Icon(
                    painter = painterResource(id = R.drawable.logout_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        // replace it with a dialog for friendly use
                        // move the clickable to the whole row
                        // navigate to sign in screen
                    })
                Text(
                    text = "Log Out",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 15.dp)
                )

            }



        }
    }



}

@Composable
fun CustomDialog(
    dismissDialog : Boolean,
    onDismissRequest: ()-> Unit,
    onConfirm : (String)->Unit,
    passedName : String
){
    var newName by remember {
        mutableStateOf(passedName)
    }
    if (dismissDialog){
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            confirmButton = {
                TextButton(onClick = { onConfirm(newName) })
                {
                    Text(text = "Confirm")
                }
                            },
            title = {
                Text(text = "Change Your Name!!")
            },
            text = {
                OutlinedTextField(value = newName , onValueChange = {newName = it})
            },
            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = "Dismiss")
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = androidx.compose.material.MaterialTheme.colors.surface

            )
    }
}