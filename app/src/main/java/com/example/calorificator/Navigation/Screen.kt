package com.example.calorificator.Navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("Home_screen")
    object Welcome : Screen( "welcome_screen")

    object Onboarding : Screen("Onboarding_screen")
}