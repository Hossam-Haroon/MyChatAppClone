package com.example.mychatappclone.userInterface.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mychatappclone.R
import com.example.mychatappclone.ui.theme.Blue40
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


@Composable
fun PhoneEnteringPage(
    navController: NavController,
    activity: ComponentActivity,
    firebaseAuth: FirebaseAuth
){
    var phoneNumber  = remember {
        mutableStateOf("")
    }
    var countryCode = remember{
        mutableStateOf("+20")
    }
    var verificationId = remember {
        mutableStateOf("")
    }
    var token = remember {
        mutableStateOf<PhoneAuthProvider.ForceResendingToken?>(null)
    }

    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.chaticon),
            contentDescription = null,
            Modifier.size(35.dp))
        Text(
            text = "OTP Verification",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center)
        Text(
            text = "we will send OTP to your number",
            fontSize = 15.sp,
            fontWeight = FontWeight.Light,
            color = Color.Gray

        )
        Row (Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){

            CountryCodePicker(selectedCountry = countryCode.value) {
                countryCode.value = it
            }

           /* OutlinedTextField(value = countryCode.value, onValueChange = {countryCode.value = it},
                modifier = Modifier.width(80.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                )*/
            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = phoneNumber.value, onValueChange = {phoneNumber.value  = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                placeholder = {
                    Text(text = "1234567890")
                },
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        Button(

            onClick = {
                // make firebase send verification code
                sendVerificationCode("${countryCode.value}${phoneNumber.value.toInt()}",activity,{verId,tok->
                    verificationId.value = verId
                    token.value = tok
                },{number->
                    navController.currentBackStackEntry?.savedStateHandle?.set("resending_token",token.value)
                    navController.navigate("verification/$number/${verificationId.value}")
                },firebaseAuth)

            },
            colors = ButtonDefaults.buttonColors(Blue40),
            shape = RoundedCornerShape(8.dp)

        ) {
            Text(text = "Send", fontSize = 16.sp)
        }


    }
}


fun sendVerificationCode(
    number : String,
    activity : ComponentActivity,
    onCodeSent: (String,PhoneAuthProvider.ForceResendingToken)->Unit,
    navigateToOtp : (String)->Unit,
    firebaseAuth: FirebaseAuth){
    //val auth = FirebaseAuth.getInstance()
    val options = PhoneAuthOptions.newBuilder(firebaseAuth)
        .setPhoneNumber(number)
        .setTimeout(60L,TimeUnit.SECONDS)
        .setActivity(activity)
        .setCallbacks(object: PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Log.d("TAG", "onVerificationCompleted: ${p0.smsCode}")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(activity,"verification failed!!",Toast.LENGTH_LONG).show()
                Log.e("PhoneAuth", "Verification Failed : $p0")
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, token)
                onCodeSent(verificationId, token)
                navigateToOtp(number)
            }

        })
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}


