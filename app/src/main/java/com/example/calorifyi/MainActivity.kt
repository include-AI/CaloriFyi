package com.example.calorifyi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.calorifyi.Navigation.SetupNavGraph
import com.example.calorifyi.ui.theme.CaloriFyiTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriFyiTheme {

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

@Preview
@Composable
fun CaloriFyiPreview(){
    CaloriFyiTheme{
    }
}

