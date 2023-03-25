package com.example.calorifyi

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calorifyi.Navigation.BottomMenuContent
import kotlinx.coroutines.launch
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import com.example.calorifyi.ui.theme.*


@Composable
fun HomeScreen(currentCalorie: Int = 1500, limitingCalorie: Int = 3000){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var searchText by remember {
        mutableStateOf(TextFieldValue())
    }
    val camContext = LocalContext.current

    Column {
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
                            Column() {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.dp)
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Calories Remaining",
                                            style = MaterialTheme.typography.h5,
//                                            fontWeight = FontWeight.Bold,
                                            fontFamily = roboto
                                        )
                                        Text(
                                            text = "$currentCalorie / $limitingCalorie",
                                            style = MaterialTheme.typography.body1
                                        )
                                    }
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.wrapContentSize()
                                    ) {
                                        CircularProgressBar(percentage = 0.5f, number = 100)

                                    }
                                }
                                Column (
                                    modifier = Modifier
                                        .wrapContentSize()
                                        ){
                                    macrosList(name = "Protein")
                                    macrosList(name = "Carbs")
                                    macrosList(name = "Fats")
                                }



//                                Column(
//                                    modifier = Modifier.fillMaxWidth()
//                                ){
//                                    macrosList(name = Protein)
//                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(214.dp))
                        BottomMenu(items = listOf(
                            BottomMenuContent("Home", R.drawable.home),
                            BottomMenuContent("Progress", R.drawable.progress),
                            BottomMenuContent("Analysis", R.drawable.analysis),
                            BottomMenuContent("Diet", R.drawable.diet)))

                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
1
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


@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 35.dp,
    color: Color = colorResource(id = R.color.usepurple),
    strokeWidth: Dp = 4.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
    ){
     var animationPlayed by remember {
         mutableStateOf(false)
     }
    val currentPercentage = animateFloatAsState(
        targetValue = if(animationPlayed) percentage else 0f,
    animationSpec = tween(
        durationMillis = animDuration,
        delayMillis = animDelay)
    )
    LaunchedEffect(key1 = true){
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ){
        Canvas(modifier = Modifier.size(radius * 2f)){
            drawArc(
                color = color,
                -90f,
                360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
        }
        Text(
            text = (currentPercentage.value * number).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun macrosList(name: String){
    Surface(
        color = colorResource(id = R.color.bgpurple),
        modifier = Modifier.padding(vertical = 1.dp, horizontal = 8.dp)
    ){
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()) {
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(text = name, fontSize = 14.sp, fontFamily = roboto)
                    Text(text = "- / -")
                }
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = "+", fontFamily = roboto)
                }
            }
        }
    }
}

@Composable
fun BottomMenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighLightColor: Color = colorResource(id = R.color.bgpurple),
    activeTextColor: Color = Color.Black,
    inactiveTextColor: Color = Color.Black,
    initialSelectedItemIndex: Int = 0)
{
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.usepurple))
            .padding(15.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        items.forEachIndexed {index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighLightColor = activeHighLightColor,
                activeTextColor=activeTextColor,
                inactiveTextColor = inactiveTextColor){
                selectedItemIndex = index
        }
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighLightColor: Color = Color.Black,
    inactiveTextColor: Color = Black,
    activeTextColor: Color = Color.Black,
    onItemClick: () -> Unit)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable{
                onItemClick
            }
    ) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) activeHighLightColor else Color.Transparent)
            .padding(10.dp)
    ){
        Icon(
            painter = painterResource(id = item.iconId),
            contentDescription = item.title,
            tint = if(isSelected) activeTextColor else inactiveTextColor,
            modifier = Modifier.size(15.dp)
        )
    }
        Text(
            text = item.title,
            color = if(isSelected) activeTextColor else inactiveTextColor,
            fontSize = 13.sp)

    }
}

@Preview
@Composable
fun SideDrawerPreview(){
    CaloriFyiTheme {
        BottomMenu(items =  listOf(
            BottomMenuContent(title = "Home", R.drawable.ic_nhome_foreground)
        ))
    }
}
