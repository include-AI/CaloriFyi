package com.example.calorificator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.calorificator.Navigation.Screen
import com.example.calorificator.ui.theme.splashcolor

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(splashcolor)
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.calorifyi))
        val logoAnimationState =
            animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress }
        )
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
            navController.navigate(Screen.Onboarding.route)
        }

    }
}