package com.example.calorifyi

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.example.calorifyi.ui.theme.CalorificatorTheme
import java.io.File

class PredictionActivity2 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorificatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val img2 = intent.getStringExtra("img2")
                    MakePrediction(ctx = LocalContext.current, img2 = img2)
                }
            }
        }
    }
}

@Composable
fun MakePrediction(ctx: Context, img2: String?){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val imgFile = File(img2)
        Image(
            painter = rememberImagePainter(data = imgFile),
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalorificatorTheme {

    }
}