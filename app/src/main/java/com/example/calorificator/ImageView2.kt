package com.example.calorificator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.calorificator.TensorFlowHelper.imageSize
import com.example.calorificator.ui.theme.CalorificatorTheme
import java.io.File

class ImageView2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorificatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var img = intent.getStringExtra("img")
                    DisplayImage(ctx = LocalContext.current, img = img)
                }
            }
        }
    }


}

@Composable
fun DisplayImage(ctx: Context, img: String?) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var imgBitmap: Bitmap? = null
        val imgFile = img?.let { File(it) }
        if (imgFile != null) {
            if (imgFile.exists()){
                imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            }

            val scaledBitmap = imgBitmap?.let { Bitmap.createScaledBitmap(it, imageSize, imageSize, false) }

            Box(
                modifier = Modifier
            ) {
                Image(
                    painter = rememberImagePainter(data = imgFile),
                    contentDescription = "image",
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
//                    FloatingActionButton(
//                        onClick = {
//                            val i = Intent(ctx, ImageView3::class.java)
//                            i.putExtra("prediction", scaledBitmap)
//                            ctx.startActivity(i)
//                        },
//                        shape = (RoundedCornerShape(10.dp)),
//                        modifier = Modifier
//                            .align(Alignment.BottomCenter)
//                            .padding(bottom = 100.dp)
//                    ) {
//                        Text(
//                            text = "Predict",
//                            color = Color.White,
//                            fontSize = 20.sp,
//                            fontFamily = FontFamily.SansSerif,
//                            modifier = Modifier
//                                .padding(start = 50.dp, end = 50.dp)
//                        )
//                    }
                    TensorFlowHelper.classifyImage(scaledBitmap) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {


                            Text(text = "Image is classified as:")
                            Text(text = it, color = Color.White, fontSize = 24.sp)
                        }
                    }

                }
            }
        }

    }
}


