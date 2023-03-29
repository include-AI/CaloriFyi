package com.example.calorifyi.Navigation

import com.example.calorifyi.R

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("Home_screen")
    object Welcome : Screen("welcome_screen")
    object Onboarding : Screen("Onboarding_Screen")
    object CameraView : Screen("camera_view")
    object AppView : Screen("app_view")
    object LogIn : Screen("LogIn")
    object SignUp : Screen("SignUp")
}

sealed class BottomNavigationItems(val route: String, var icon: Int, var title: String){
    object Home : BottomNavigationItems("home_screen", R.drawable.home, "Home")
    object Progress : BottomNavigationItems("progress_view", R.drawable.progress, "Progress")
    object Analysis : BottomNavigationItems("analysis_view", R.drawable.analysis, "Analysis")
    object Diet : BottomNavigationItems("diet_view", R.drawable.diet, "Diet")
}


