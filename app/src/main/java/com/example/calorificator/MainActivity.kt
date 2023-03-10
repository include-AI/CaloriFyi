package com.example.calorificator

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.calorificator.Navigation.SetupNavGraph
import com.example.calorificator.ui.theme.CalorificatorTheme
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorificatorTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = BGPurple
//                ) {
//                    HomeScreen()
//                }
            }
        }
    }

}

@Composable
fun Calorificator(){
}

@Preview
@Composable
fun CalorificatorpPreview(){
    CalorificatorTheme {
    }
}

