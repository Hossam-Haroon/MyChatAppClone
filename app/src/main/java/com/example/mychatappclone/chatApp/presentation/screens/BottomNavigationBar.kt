package com.example.mychatappclone.chatApp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mychatappclone.core.presentation.BottomNavItems
import com.example.mychatappclone.R
import com.example.mychatappclone.ui.theme.Blue40
import com.example.mychatappclone.ui.theme.Blue60

@Composable
fun BottomNavigationBar(
    pagerState: PagerState,
    onPageSelected: (Int)-> Unit
){
    BottomNavigation (Modifier.height(70.dp), backgroundColor = Blue40){
        val items = listOf(
            BottomNavItems("Messages", painterResource(id = R.drawable.message_icon)),
            BottomNavItems("News", painterResource(id = R.drawable.news_icon)),
            BottomNavItems("Societies", painterResource(id = R.drawable.society_icon)),
            BottomNavItems("Calls", painterResource(id = R.drawable.call_icon))



        )

        items.forEachIndexed { index, items ->
            val selected = pagerState.currentPage == index
            val iconColor = if (selected) Blue60 else Color.White

            BottomNavigationItem(selected = selected, onClick = {
                onPageSelected(index)
            }, icon = {
                Column (Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                    Icon(painter = items.Icon, contentDescription = null, tint = iconColor)
                    Text(text = items.label, color = iconColor, fontSize = 10.sp)
                }
            })
        }



    }

}