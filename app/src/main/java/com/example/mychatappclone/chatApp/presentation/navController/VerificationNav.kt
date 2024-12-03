package com.example.chattingapp.userInterface.nav

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mychatappclone.chatApp.presentation.screens.AddNewContact
import com.example.mychatappclone.chatApp.presentation.screens.AddSingleContactScreen
import com.example.mychatappclone.chatApp.presentation.screens.EnterPersonalDataScreen
import com.example.mychatappclone.chatApp.presentation.screens.MainScreen
import com.example.mychatappclone.chatApp.presentation.screens.MessagesScreen
import com.example.mychatappclone.chatApp.presentation.screens.OtpVerification
import com.example.mychatappclone.chatApp.presentation.screens.PhoneEnteringPage
import com.example.mychatappclone.chatApp.presentation.screens.SettingsScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun VerificationNav(activity: ComponentActivity, firebaseAuth: FirebaseAuth){
    val navController = rememberNavController()
    val destination = if (firebaseAuth.currentUser?.uid?.isNotEmpty() == true){
            "mainScreen"
        }else{
            "login"
        }

    NavHost(navController = navController, startDestination = destination){
        composable("login") {
                PhoneEnteringPage(navController,activity,firebaseAuth)
        }
        composable(
            "verification/{number}/{verificationId}",
            arguments = listOf(
                navArgument("number"){type = NavType.StringType},
                navArgument("verificationId"){type = NavType.StringType}
            )
        ) {backStackEntry->
            val number = backStackEntry.arguments?.getString("number") ?: ""
            val verificationId = backStackEntry.arguments?.getString("verificationId") ?: ""
            OtpVerification(number,verificationId, activity,navController,firebaseAuth)
        }

        composable("mainScreen") {
            MainScreen(navController)
        }
        composable("enterPersonalDataScreen/{number}/{userId}",
            arguments = listOf(
                navArgument("number"){type = NavType.StringType}
            )) {backStackEntry->
            val number = backStackEntry.arguments?.getString("number") ?: ""
            EnterPersonalDataScreen(number,navController)
        }
        composable("settingsScreen") {
            SettingsScreen(navController)
        }
        composable("addSingleContentScreen") {
            AddSingleContactScreen(navController)
        }
        composable("addNewContactScreen") {
            AddNewContact(navController)
        }
        composable("messagesScreen") {
            MessagesScreen(navController)
        }
    }
}