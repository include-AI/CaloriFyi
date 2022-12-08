package com.example.calorificator.Navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("Home_screen")
}