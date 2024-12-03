package com.example.mychatappclone.chatApp.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chattingapp.viewModel.MyViewModel
import com.example.mychatappclone.R
import com.example.mychatappclone.ui.theme.Blue40

import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@Composable
fun OtpVerification(
    number : String,
    verId : String,
    activity: ComponentActivity,
    navController: NavController,
    firebaseAuth: FirebaseAuth
){
    val c1 = remember{ mutableStateOf("")}
    val c2 = remember{ mutableStateOf("")}
    val c3 = remember{ mutableStateOf("")}
    val c4 = remember{ mutableStateOf("")}
    val c5 = remember{ mutableStateOf("")}
    val c6 = remember{ mutableStateOf("")}
    
    var timer = remember {
        mutableStateOf(60)
    }
    var isWorking = remember {
        mutableStateOf(true)
    }
    var verificationId = remember {
        mutableStateOf(verId)
    }
    val resendingToken = navController.previousBackStackEntry?.savedStateHandle?.get<PhoneAuthProvider.ForceResendingToken>("resending_token")
    var token = remember {
        mutableStateOf(resendingToken)
    }
    val viewModel : MyViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val timerText = String.format("%02d:%02d", timer.value/60,timer.value%60)
    
    LaunchedEffect(Unit) {
        while(timer.value > 0 && isWorking.value){
            delay(1000)
            timer.value--
        }
        if (timer.value <= 0){
            isWorking.value = false
        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.verificationmin),
            contentDescription =null,
            modifier = Modifier.size(45.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Verify OTP", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Enter your received OTP here", fontSize = 13.sp, color = Color.Gray)
        Text(text = number, fontSize = 13.sp,color = Color.Gray)
        Spacer(Modifier.height(40.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            OutlinedTextField(value = c1.value, onValueChange ={newValue->
                if (newValue.length <= 1 && newValue.all { it.isDigit() }){
                    c1.value = newValue
                }
               },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 5.dp),
                maxLines = 1)
            OutlinedTextField(value = c2.value , onValueChange ={newValue->
                if (newValue.length <= 1 && newValue.all { it.isDigit() }){
                    c2.value = newValue
                }
            },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 5.dp),
                maxLines = 1 )
            OutlinedTextField(value = c3.value, onValueChange ={newValue->
                if (newValue.length <= 1 && newValue.all { it.isDigit() }){
                    c3.value = newValue
                }
            },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 5.dp),
                maxLines = 1 )
            OutlinedTextField(value = c4.value, onValueChange ={newValue->
                if (newValue.length <= 1 && newValue.all { it.isDigit() }){
                    c4.value = newValue
                }
            },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 5.dp),
                maxLines = 1 )
            OutlinedTextField(value = c5.value, onValueChange ={newValue->
                if (newValue.length <= 1 && newValue.all { it.isDigit() }){
                    c5.value = newValue
                }
            },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 5.dp),
                maxLines = 1 )
            OutlinedTextField(value = c6.value, onValueChange = {newValue->
                if (newValue.length <= 1 && newValue.all { it.isDigit() }){
                    c6.value = newValue
                }
            },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 5.dp),
                maxLines = 1)

        }
        Spacer(modifier = Modifier.height(20.dp))
        if (isWorking.value){
            Text(text = "Code Verification Resend in... $timerText", textAlign = TextAlign.Center, fontSize = 13.sp, color = Color.Gray)
        }else{
            Row (horizontalArrangement = Arrangement.Center){
                Text(
                    text = "Don't get the OTP?",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 3.dp)
                )
                Text(
                    text = "RESEND OTP",
                    fontSize = 14.sp,
                    color = Color.Red,
                    modifier = Modifier.clickable {
                        forceResendingToken(number,activity,resendingToken!!,firebaseAuth){verId,tok->
                            verificationId.value = verId
                            token.value = tok
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
        Button(
            colors = ButtonDefaults.buttonColors(Blue40),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                if (c1.value.isEmpty() || c2.value.isEmpty()|| c3.value.isEmpty()|| c4.value.isEmpty()|| c5.value.isEmpty()|| c6.value.isEmpty()){
                    Toast.makeText(activity,"Please Enter Verification Code", Toast.LENGTH_LONG).show()
                }else{
                    if (verificationId.value != null){
                        val smsCode = "${c1.value}${c2.value}${c3.value}${c4.value}${c5.value}${c6.value}"

                        val credential = PhoneAuthProvider.getCredential(verificationId.value,smsCode)
                        Log.d("lookForError", "$smsCode : ${verificationId.value} : $credential ")
                        Firebase.auth.signInWithCredential(credential)
                            .addOnCompleteListener {task->
                                if (task.isSuccessful){
                                    //another if to determine if the user already exist in the firebase or not
                                    coroutineScope.launch {
                                        if (viewModel.checkUserExistence(number)){
                                            navController.navigate("mainScreen")
                                        }else{
                                            Log.d("checkNumber", number)
                                            Toast.makeText(activity,"Welcome ${task.result}",Toast.LENGTH_LONG).show()
                                            Log.d("Account","${task.result.user}")
                                            navController.navigate("enterPersonalDataScreen/${number}/${task.result.user?.uid}")
                                        }
                                    }
                                }else{
                                    Toast.makeText(activity,"OTP is no valid",Toast.LENGTH_LONG).show()
                                }

                            }
                    }
                }
                // check the correction of the verification code: $c1$c2$c3$c4$c5$c6
                // navigate to the main screen
            },
            ) {
                Text(text = "VERIFY", fontSize = 15.sp)
        }

    }
}

fun forceResendingToken(
    number : String,
    activity : ComponentActivity,
    token : PhoneAuthProvider.ForceResendingToken,
    firebaseAuth: FirebaseAuth,
    onCodeSent: (String,PhoneAuthProvider.ForceResendingToken)->Unit
    ){
    //val auth = FirebaseAuth.getInstance()
    val options = PhoneAuthOptions.newBuilder(firebaseAuth)
        .setPhoneNumber(number)
        .setTimeout(60L, TimeUnit.SECONDS)
        .setActivity(activity)
        .setCallbacks(object: PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("TAG", "onVerificationCompleted: ${credential.smsCode}")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(activity,"verification failed!!",Toast.LENGTH_LONG).show()
                Log.e("PhoneAuth", "Verification Failed")
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, token)
                onCodeSent(verificationId, token)
            }

        })
        .setForceResendingToken(token)
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}




