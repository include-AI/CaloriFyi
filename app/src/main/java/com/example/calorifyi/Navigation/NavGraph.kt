package com.example.calorifyi.Navigation

import SignUp_Additional
import SignUp_Personal
import SignUp_User
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calorifyi.*
import com.example.calorifyi.HomeScreen
import com.example.calorifyi.OnBoarding
import com.example.calorifyi.RegistrationViewModel.RegistrationViewModel
import com.example.calorifyi.SplashScreen
import com.example.calorifyi.User.LogIn
import com.example.calorifyi.WelcomeScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    val viewModel: RegistrationViewModel = viewModel()
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
        composable(route = Screen.LogIn.route){
            LogIn(navController = navController)
        }
        composable(route = Screen.SignUpPersonal.route){
            SignUp_Personal(navController = navController, viewModel)
        }
        composable(route = Screen.SignUpUser.route){
            SignUp_User(navController = navController, viewModel)
        }
        composable(route = Screen.SignUpAdditional.route){
            SignUp_Additional(navController = navController, viewModel)
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