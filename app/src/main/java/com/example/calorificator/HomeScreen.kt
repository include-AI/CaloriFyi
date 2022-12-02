package com.example.calorificator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.calorificator.ui.theme.BGPurple
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var searchText by remember {
        mutableStateOf(TextFieldValue())
    }
    Column() {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                SideDrawer()
            },
            backgroundColor = BGPurple,
            contentColor = Color.Black,
            topBar = {
                TopAppBar(
                    modifier = Modifier
                        .height(80.dp),
                    backgroundColor = BGPurple,
                ) {
                    IconButton(
                        onClick = {
                            scope.launch{ scaffoldState.drawerState.open() }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.menu_48px),
                            contentDescription = "menu",
                            Modifier.size(40.dp))
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .height(55.dp)
                            .width(250.dp),
                        value = searchText,
                        onValueChange = { newText ->
                            searchText = newText
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(50),
                    )
                    Row(){
                        IconButton(
                            modifier = Modifier.weight(1.5f),
                            onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.add_circle_48px),
                                contentDescription = "user_blog"
                            )
                        }
                        IconButton(
                            modifier = Modifier.weight(1.5f),
                            onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.account_circle_48px),
                                contentDescription = "user_login"
                            )
                        }
                    }
                }

            },
            content = {

            }
        )
    }

}

@Composable
fun SideDrawer(){
    Column(
        modifier = Modifier
            .background(BGPurple)
            .fillMaxSize()
    ) {
        Text(text = "Name")
    }
}