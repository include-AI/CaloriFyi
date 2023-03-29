package com.example.calorifyi.Navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("Home_screen")
    object Welcome : Screen( "welcome_screen")

    object Onboarding : Screen("Onboarding_Screen")
    object CameraView : Screen("camera_view")
    object LogIn : Screen("LogIn")
    object SignUp: Screen("SignUp")
}