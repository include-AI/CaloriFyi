package com.example.calorifyi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.calorifyi.ui.theme.CaloriFyiTheme
import com.example.calorifyi.ui.theme.Purple200
import com.example.calorifyi.ui.theme.googleSans
import com.example.calorifyi.ui.theme.onb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.DriverManager

//receiving objects
data class Reception(val quantity: Int, val calories: Int)

class ReceptionActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriFyiTheme {
                //taking the inputs to the next activity, i.e., name of the food into modelOutput
                var foodImage: Uri? = intent.data
                var modelOutput = intent.getStringExtra("query_image")
                CalorieDisplay(modelOutput, foodImage)
            }
        }
    }
}

    //this is the main thread
@Composable
fun CalorieDisplay(modelOutput: String?, foodImage: Uri?) {
    var reception by remember(modelOutput) {
        mutableStateOf(emptyList<Reception>())
    }
    var context: Context

    //async task
    LaunchedEffect(modelOutput) {
        val newReception = recep(modelOutput)
        reception = newReception
    }
    //displaying
    Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(onb)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(
                    Alignment.Center
                )
            ){
                Image(
                    painter = rememberImagePainter(data = foodImage),
                    contentDescription = "foodImage",
                    modifier = Modifier
                        .size(250.dp)
                        .padding(top = 20.dp),
                )
                Text(text = "$modelOutput")
                Spacer(modifier = Modifier.height(20.dp))
                context = LocalContext.current
                Button(
                    modifier = Modifier.size(height = 40.dp, width = 160.dp),
                    onClick = {
                        val intent = Intent(context, CameraActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                        (context as Activity).finish()
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(Purple200)
//
//                            context.startActivity(Intent(context, CameraActivity::class.java))
//                        finish()}
                    ){
                    Text(text = "Retake Image", color = Color.White, fontFamily = googleSans)
                }
            }
        }
        DisplayReception(reception)
    }
}

//making connection

suspend fun recep(modelOutput: String?) = withContext(Dispatchers.IO) {
    val host = "SQL8002.site4now.net"
    val port = "1433"
    val database = "db_a963e4_quantunfcc"
    val username = "db_a963e4_quantunfcc_admin"
    val password = "Pratyushkr.123@"
    val url = "jdbc:jtds:sqlserver://$host:$port/$database"
    val connection = DriverManager
        .getConnection(url, username, password)

    // the query is only prepared not executed

    val query = connection.prepareStatement("SELECT Calories, Quantity FROM test WHERE Name_of_Food = '$modelOutput'")
    // the query is executed and results are fetched
    val result = query.executeQuery()
    // an empty list for holding the results
    val reception = mutableListOf<Reception>()

    while (result.next()) {
        val quantity = result.getInt("Quantity")
        val calories = result.getInt("Calories")
        reception.add(Reception(quantity, calories))
    }
    return@withContext reception
}

@Composable
fun DisplayReception(reception: List<Reception>) {
//        val reception by remember { mutableStateOf(emptyList<Reception>()) }

//        LaunchedEffect(Unit) {
//
//        }

    // Display the fetched data
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(start = 15.dp)
    ) {
        reception.forEach { item ->
            Text("\n\nPer ${item.quantity} gm, there is: \n\nCalories: ${item.calories}", fontFamily = googleSans)
        }
    }
}

