package com.example.calorifyi

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.calorifyi.ui.theme.CaloriFyiTheme
import com.example.calorifyi.ui.theme.googleSans
import java.io.File

class PredictionActivity2 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriFyiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}

@Composable
fun MakePrediction(){
    val context = LocalContext.current
    val path = context.getExternalFilesDir(null)!!.absolutePath
    val imagePath = "$path/tempFile.jpg"

    val image = BitmapFactory.decodeFile(imagePath)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ObjectDetectionHelper.ObjectDetect(bitmap = image) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var reception by remember(it) {
                    mutableStateOf(emptyList<Reception>())
                }
                LaunchedEffect(it) {
                    val newReception = recep(it)
                    reception = newReception
                }

//                        Text(text = "Image is classified as:", fontFamily = googleSans)
                Text(
                    text = it,
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontFamily = googleSans
                )
                reception.forEach { item ->
                    val tableData = listOf(
                        Pair("Quantity: ", "${item.quantity} gm"),
                        Pair("Calories: ", "${item.calories} kcal"),
                        Pair("Fats: ", "${item.fats} gm"),
                        Pair("Carbs: ", "${item.carbs} gm"),
                        Pair("Proteins: ", "${item.proteins} gm")
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    LazyColumn {
                        items(tableData.size) { index ->
                            val rowData = tableData[index]
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = rowData.first,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 16.dp)
                                )
                                Text(
                                    text = rowData.second,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 16.dp),
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }

                }



            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CaloriFyiTheme {

    }
}