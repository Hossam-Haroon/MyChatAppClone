package com.example.mychatappclone.chatApp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController

import com.example.mychatappclone.R
import com.example.mychatappclone.ui.theme.Blue40
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun MainScreen(navController: NavController){
    var expanded by remember {
        mutableStateOf(false)
    }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = {4})
    val coroutineScope = rememberCoroutineScope()

    //val receivedUserData = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user_data")
    Scaffold (topBar = {
        Box (
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Blue40),
        ){
            Row (
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ){
                Row (
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.button_more),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable {
                                expanded = true
                            }
                    )
                    Icon(
                        painter =painterResource(id = R.drawable.baseline_search_24) ,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.camera_icon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

                Text(
                    text = "ChatApp",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(end = 9.dp)
                )

            }

        }
    },
        bottomBar = {
            BottomNavigationBar(pagerState = pagerState) {page->
                coroutineScope.launch { pagerState.animateScrollToPage(page) }
                
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //navController.currentBackStackEntry?.savedStateHandle?.set("user_data",receivedUserData)
                    navController.navigate("addNewContactScreen")
                    // move to other screen to add groups or another person
                },
                contentColor = Color.White,
                containerColor = Blue40,
                modifier = Modifier.padding(bottom = 17.dp, start = 5.dp),
                content = {
                    Icon(
                        painter = painterResource(R.drawable.add_button_icon),
                        contentDescription = null
                        , tint = Color.White)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Start){

        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize() ) {page->
            when(page){
                0-> {
                    /*navController
                        .currentBackStackEntry?.savedStateHandle?.set("user_data", receivedUserData)*/
                    MessagesScreen(navController)
                }
                1-> NewsScreen()
                2-> SocietiesScreen()
                3-> CallsScreen()

            }

        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false })
        {
            DropdownMenuItem(text = { 
                Text(text = "New Group", fontSize = 14.sp)
            }, onClick = { /*TODO*/ })
            DropdownMenuItem(text = {
                Text(text = "Favourite Messages", fontSize = 14.sp)
            }, onClick = { /*TODO*/ })
            DropdownMenuItem(text = {
                Text(text = "Settings", fontSize = 14.sp)
            }, onClick = {
                expanded = false
                navController.navigate("settingsScreen")
            })
        }
        


    }


}