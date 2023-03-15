package com.example.calorifyi.Navigation

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calorifyi.HomeScreen
import com.example.calorifyi.OnBoarding
import com.example.calorifyi.SplashScreen
import com.example.calorifyi.WelcomeScreen
import com.google.accompanist.pager.ExperimentalPagerApi


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Onboarding.route) {
            OnBoarding(navController = navController)
        }
        composable(route = Screen.Home.route){
            HomeScreen()
        }
        composable(route = Screen.Welcome.route){
            WelcomeScreen(navController = navController)
        }
    }
}


//class BooleanPreference(private val preferences: SharedPreferences,
//                        private val key: String,
//                        private val defaultValue: Boolean = false) {
//    fun get(): Boolean = preferences.getBoolean(key, defaultValue)
//    fun set(value: Boolean) = preferences.edit().putBoolean(key, value).apply()
//}
//class OnboardingViewModel(application: Application) : AndroidViewModel(application) {
//    private val preferences = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
//    private val onboardingShownPref = BooleanPreference(preferences, "onboardingShown")
//
//    val onboardingShown: Boolean
//        get() = onboardingShownPref.get()
//
//    fun setOnboardingShown() {
//        onboardingShownPref.set(true)
//    }
//}
//val onboardingViewModel: OnboardingViewModel = navControllerViewModel()
//if (!onboardingViewModel.onboardingShown) {
//}
//onboardingViewModel.setOnboardingShown()