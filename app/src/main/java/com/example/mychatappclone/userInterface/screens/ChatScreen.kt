package com.example.mychatappclone.userInterface.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mychatappclone.R
import com.example.mychatappclone.ui.theme.Blue40

@Composable
@Preview
fun ChatScreen(){
    var text by remember{ mutableStateOf("")}
    Column {
        LazyColumn {
            // apply the messages here from firestore
        }
        Row (verticalAlignment = Alignment.CenterVertically){
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(3f)
            )
            Button(
                onClick = {
                //send the message
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.padding(start = 10.dp, end = 7.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.send_icon), contentDescription = null,
                    tint = Blue40)
            }
        }
    }
}