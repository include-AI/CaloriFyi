package com.example.calorifyi

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.calorifyi.Navigation.OnboardingItems
import com.example.calorifyi.Navigation.Screen
import com.example.calorifyi.ui.theme.onb
import com.example.calorifyi.ui.theme.roboto
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun OnBoarding(navController: NavHostController) {
    val items = OnboardingItems.OnBoardingItems.getData()
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState()
    var shouldshowGS by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(onb))
    {
        TopSection(
            onBackClick = {
                if (pageState.currentPage + 1 > 1) scope.launch {
                    pageState.scrollToPage(pageState.currentPage - 1)
                }
            },
            onSkipClick = {
                navController.navigate(Screen.LogIn.route)
            }
        )

        HorizontalPager(
            count = items.size,
            state = pageState,
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth()
        ) { page ->
            OnBoardingItem(items = items[page])
        }
        BottomSection(
            size = items.size,
            index = pageState.currentPage,
            navController = navController,
        ) {
            if (pageState.currentPage + 1 < items.size) scope.launch {
                pageState.scrollToPage(pageState.currentPage + 1)
                if (pageState.currentPage==2){

                }
            }
        }

    }
}

@Composable
fun GS(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = { navController.navigate(Screen.Home.route) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Get Started")
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TopSection(onBackClick: () -> Unit = {}, onSkipClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(17.dp)
    ) {
        // Back button
        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = null)
        }

        // Skip Button
        TextButton(
            onClick = onSkipClick,
            modifier = Modifier.align(Alignment.CenterEnd),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(text = "Skip", style = TextStyle(fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = roboto
            ),
                color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun BottomSection(size: Int, index: Int, navController: NavHostController, function: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {


        // Indicators
        Indicators(size, index)



        // FAB Next
        /* FloatingActionButton(
             onClick = onButtonClick,
            // backgroundColor = MaterialTheme.colorScheme.primary,
            // contentColor = MaterialTheme.colorScheme.onPrimary,
             modifier = Modifier.align(Alignment.CenterEnd)
         ) {
             Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "Next")
         }*/
        Button(
            onClick = {
                navController.navigate(Screen.Home.route)
            },
            modifier = Modifier
                .padding(end = 15.dp)
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
        ) {
            Icon(Icons.Outlined.KeyboardArrowRight,
                tint = Color.White,
                contentDescription = "Localized description")
        }
    }
}

@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.Center)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0XFFF8E2E7)
            )
    ) {

    }
}

@Composable
fun OnBoardingItem(items: OnboardingItems.OnBoardingItems) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = items.image),
            contentDescription = "Image1",
            modifier = Modifier
                .padding(top = 165.dp)
                .wrapContentSize()
                .clip(
                    RoundedCornerShape(12.dp)
                ),
            contentScale = ContentScale.FillBounds
        )

//        Spacer(modifier = Modifier.height(15.dp))
    }
    Box(){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = items.title),
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                // fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(top = 20.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp,
                fontFamily = roboto
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = items.desc),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(end = 20.dp)
                    .padding(top = 20.dp),
                letterSpacing = 0.sp,
                fontFamily = roboto
            )
        }
    }
}
