package com.example.calorificator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calorificator.ui.theme.BGPurple
import com.example.calorificator.ui.theme.CalorificatorTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorificatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BGPurple
                ) {
                    HomeScreen()
                }
            }
        }
    }


}

@Composable
fun Calorificator(){
    WelcomeScreen()
}

@Preview
@Composable
fun CalorificatorpPreview(){
    CalorificatorTheme {
        WelcomeScreen()
    }
}

