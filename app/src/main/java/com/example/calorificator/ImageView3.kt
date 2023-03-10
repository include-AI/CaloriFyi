package com.example.calorificator

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.calorificator.ui.theme.CalorificatorTheme


class ImageView3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorificatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var bitmap = intent.getStringExtra("prediction") as Bitmap?
                    PredictionShow(ctx = LocalContext.current, pred = bitmap)
                }
            }
        }
    }
}


@Composable
fun PredictionShow(ctx: Context, pred: Bitmap? ){
    TensorFlowHelper.classifyImage(pred) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (pred != null) {
                Image(bitmap = pred.asImageBitmap(), contentDescription = "predicted image")
            }
            else{
                Text("Yeah! Missing")
            }

            Text(text = "Image is classified as:")
            Text(text = it, color = Color.White, fontSize = 24.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    CalorificatorTheme {

    }
}