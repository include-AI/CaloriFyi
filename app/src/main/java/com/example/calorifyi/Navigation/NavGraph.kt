package com.example.calorifyi.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calorifyi.*
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
        composable(route = Screen.AppView.route) {
            CaloriFyiApp()
        }
        composable(route = Screen.Onboarding.route){
            OnBoarding(navController = navController)
        }
        composable(route = Screen.Welcome.route){
            WelcomeScreen(navController = navController)
        }
    }
}

@Composable
fun BottomNavigation_(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItems.Home.route
        ){
        composable(route = BottomNavigationItems.Home.route){
            HomeScreen()
        }
        composable(route = BottomNavigationItems.Progress.route){
            ProgressScreen(name = "Progress")
        }
        composable(route = BottomNavigationItems.Analysis.route){
            AnalysisScreen(name = "Analysis")
        }
        composable(route = BottomNavigationItems.Diet.route){
            DietScreen(name = "Diet")
        }
    }
}