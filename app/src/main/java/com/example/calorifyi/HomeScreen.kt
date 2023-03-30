package com.example.calorifyi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.calorifyi.Navigation.BottomNavigationItems
import com.example.calorifyi.Navigation.BottomNavigation_
import com.example.calorifyi.ui.theme.*



@Composable
fun CaloriFyiApp(){
    val navController = rememberNavController()
    val items = listOf(
        BottomNavigationItems.Home,
        BottomNavigationItems.Progress,
        BottomNavigationItems.Analysis,
        BottomNavigationItems.Diet
    )
    Scaffold(
        bottomBar = { BottomMenu(navController, items = items) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)){
                BottomNavigation_(navController = navController)
            }
        }
    )
}

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
                                            style = Typography.h5,
//                                            fontWeight = FontWeight.Bold,
                                            fontFamily = googleSans
                                        )
                                        Text(
                                            text = "$currentCalorie / $limitingCalorie",
                                            style = Typography.body1
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
                                    MacrosList(name = "Protein")
                                    MacrosList(name = "Carbs")
                                    MacrosList(name = "Fats")
                                }



//                                Column(
//                                    modifier = Modifier.fillMaxWidth()
//                                ){
//                                    macrosList(name = Protein)
//                                }
                            }
                        }



                    }
                }

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
            .padding(start = 8.dp, top = 32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column() {
            Text(
                text = "Menu",
                fontFamily = googleSans,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 20.dp, top = 30.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))

            LazyColumn {
                item {
                    DrawerItem(
                        title = "Calories in Gallery",
                        onClick = {
                            cam2Context.startActivity(
                                Intent(
                                    cam2Context,
                                    PredictionActivity::class.java
                                )
                            )
                        })
                    DrawerItem(title = "About Us", onClick = {})
                    DrawerItem(title = "Version Details", onClick = {})
                }
            }

        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawerItem(
    title: String,
    onClick: ()->Unit,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        backgroundColor = Purple200,
        elevation = 0.dp,
        onClick = onClick,
        shape = RoundedCornerShape(12.dp)
    ) {
//        OutlinedButton(
//            colors = ButtonDefaults.buttonColors(backgroundColor = BGPurple),
//            border = BorderStroke(0.5.dp, Purple200),
//            onClick = onClick,
////            modifier = Modifier.padding(5.dp)
//        ) {
//            Text(
//                text = title,
//                fontFamily = googleSans
//            )
//        }

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {

            Text(
                modifier = Modifier
                    .padding(start = 24.dp),
                text = title,
            )
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
fun MacrosList(name: String){
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
                    Text(text = name, fontSize = 14.sp, fontFamily = googleSans)
                    Text(text = "- / -")
                }
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = "+", fontFamily = googleSans)
                }
            }
        }
    }
}

@Composable
fun BottomMenu(
    navController: NavController,
    items: List<BottomNavigationItems>
)
{

//    val items = listOf(
//        BottomNavigationItems.Home,
//        BottomNavigationItems.Progress,
//        BottomNavigationItems.Analysis,
//        BottomNavigationItems.Diet
//    )
    BottomNavigation(
        modifier = Modifier
            .background(UsePurple)
            .height(65.dp),
        elevation = 2.dp,
        backgroundColor = UsePurple,
        contentColor = White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach {item ->
            BottomNavigationItem(
                icon = {
                       Icon(
                           painter = painterResource(id = item.icon),
                           modifier = Modifier.size(25.dp),
                           contentDescription = "icon")
                },
                label = { Text(
                    text = item.title,
                    color = White,
                    fontFamily = googleSans,
                )},
                selectedContentColor = Black,
                unselectedContentColor = White,
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
//            BottomMenuItem(
//                item = item,
//                isSelected = index == selectedItemIndex,
//                activeHighLightColor = activeHighLightColor,
//                activeTextColor=activeTextColor,
//                inactiveTextColor = inactiveTextColor,
//                onItemClick = {
//                    navController.navigate(item.route){
//                        navController.graph.startDestinationRoute?.let { route ->
//                            popUpTo(route) {
//                                saveState = true
//                            }
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                }
//            )
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomNavigationItems,
    isSelected: Boolean = false,
    activeHighLightColor: Color = Black,
    inactiveTextColor: Color = Black,
    activeTextColor: Color = Black,
    onItemClick: () -> Unit,
)
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
            painter = painterResource(id = item.icon),
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
        SideDrawer()
    }

}
