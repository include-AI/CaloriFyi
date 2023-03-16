package com.example.calorifyi

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calorifyi.ui.theme.BGPurple
import com.example.calorifyi.ui.theme.CalorificatorTheme
import kotlinx.coroutines.launch



@Composable
fun HomeScreen(){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var searchText by remember {
        mutableStateOf(TextFieldValue())
    }
    val camContext = LocalContext.current

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
                        .height(65.dp),
                    backgroundColor = BGPurple,
                    elevation = 5.dp
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
                    TextField(
                        modifier = Modifier
                            .height(55.dp)
                            .width(250.dp)
                            .border(0.dp, BGPurple)
                            .background(
                                color = MaterialTheme.colors.background,
                                shape = RoundedCornerShape(50)
                            ),
                        value = searchText,
                        onValueChange = { newText ->
                            searchText = newText
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.Black,
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                tint = MaterialTheme.colors.onBackground,
                                contentDescription = "Search Icon"
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = true,
                            keyboardType = KeyboardType.Text
                        ),
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    item {
                        Card(
                            modifier = Modifier
                                .size(400.dp)
                                .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                            shape = RoundedCornerShape(10),
                            elevation = 10.dp,
                            backgroundColor = BGPurple,

                            ) {

                        }

                    }
                }

                val contextForToast = LocalContext.current.applicationContext

                Box(modifier = Modifier.fillMaxSize()) {

                    FloatingActionButton(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 150.dp)
                            .align(alignment = Alignment.BottomCenter),
                        onClick = {
                            camContext.startActivity(Intent(camContext, CameraActivity::class.java))
                        }) {
                        Icon(
                            painter = painterResource(R.drawable.camera_48px),
                            contentDescription = "Camera")
                    }
                }


            }
        )
    }

}



@Composable
fun SideDrawer(){
    val cam2Context = LocalContext.current
    Column(
        modifier = Modifier
            .background(BGPurple)
            .fillMaxSize()
    ) {
        Column() {
            Text(
                text = "Menu",
                fontFamily = FontFamily.SansSerif,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 20.dp, top = 30.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Card() {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { cam2Context.startActivity(Intent(cam2Context, PredictionActivity::class.java)) },

                    ){
                    Text(text = "Calories in Gallery")
                }
            }

        }

    }
}


@Preview
@Composable
fun SideDrawerPreview(){
    CalorificatorTheme {
        SideDrawer()
    }
}
