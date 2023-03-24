package com.example.calorifyi

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter
import com.example.calorifyi.ui.theme.CaloriFyiTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.DriverManager

//receiving objects

data class Reception(val quantity: Int, val calories: Int)

class ReceptionActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriFyiTheme {
                var foodImage: Uri? = intent.data
                var modelOutput = intent.getStringExtra("query_image")
                CalorieDisplay(modelOutput, foodImage)
            }
        }
    }


    @Composable
    fun CalorieDisplay(modelOutput: String?, foodImage: Uri?) {
        var reception by remember(modelOutput) {
            mutableStateOf(emptyList<Reception>())
        }

        LaunchedEffect(modelOutput) {
            val newReception = recep(modelOutput)
            reception = newReception
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(data = foodImage),
                contentDescription = "foodImage",
                modifier = Modifier.wrapContentSize()
            )

            Text(text = "$modelOutput")
            DisplayReception(reception)
        }
    }

//function

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
        val query = connection.prepareStatement("SELECT Calories, Quantity FROM calories_dataset WHERE Name_of_Food = '$modelOutput'")
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
        Column {
            reception.forEach { item ->
                Text("Quantity: ${item.quantity}, Calories: ${item.calories}")
            }
        }
    }
//    @Composable
//    fun DisplayReception(modelOutput: String?) {
//        var reception by remember { mutableStateOf(emptyList<Reception>()) }
//
//        LaunchedEffect(Unit) {
//            // Launch a coroutine to fetch the data and update the state
//            val data = recep(modelOutput)
//            reception = data
//        }
//
//        // Display the fetched data
//        Column {
//            reception.forEach { item ->
//                Text("Quantity: ${item.quantity}, Calories: ${item.calories}")
//            }
//        }
//    }
}


//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Surface
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.calorifyi.ui.theme.CaloriFyiTheme
//import java.sql.DriverManager
//
//class ReceptionActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            CaloriFyiTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    var modelOutput = intent.getStringExtra("query_image")
//                    CalorieDisplay(id = modelOutput)
//                }
//            }
//        }
//    }
//
//
//}

//
////receiving objects
//
//data class Reception(val quantity: Int, val calories: Int)
//
//@Composable
//fun CalorieDisplay(id: String?) {
//    val host = "SQL8002.site4now.net"
//    val port = "1433"
//    val Classes = "net.sourceforge.jtds.jdbc.Driver"
//    val database = "db_a963e4_quantunfcc"
//    val username = "db_a963e4_quantunfcc_admin"
//    val password = "Pratyushkr.123@"
//
//    val url_ = "jdbc:jtds:sqlserver://$host:$port/$database"
//
//
//    LaunchedEffect(Unit){
//        try {
//            val url = url_
//            val connection = DriverManager
//                .getConnection(url, username, password)
//            println(connection.isValid(0))
//
//
////     the query is only prepared not executed
//            val query =
//                connection.prepareStatement("SELECT Calories, Quantity FROM calories_dataset WHERE Name_of_Food = $id")
////     the query is executed and results are fetched
//            val result = query.executeQuery()
//            // an empty list for holding the results
//            val reception = mutableListOf<Reception>()
//
//            while (result.next()) {
//
//                val quantity = result.getInt("Quantity")
//                val calories = result.getInt("Calories")
//
//                reception.add(Reception(quantity, calories))
//
//            }
//
////
////            val responseCode = connection.responseCode
////
////            if (responseCode == HttpURLConnection.HTTP_OK) {
////                val inputStream = connection.inputStream
////                // Read the data from the input stream
////                val data = inputStream.bufferedReader().use(BufferedReader::readText)
////                inputStream.close()
////
////                // Update the state with the downloaded data
////                textState.value = data
////            } else {
////                // Handle the error
////                textState.value = "Error downloading data"
////            }
//        } catch (e: Exception) {
//            // Handle any exceptions
//            print("Error")
//        }
//    }
//
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        var infoFor = recep(id=id)
//        Text(text = "$infoFor")
//    }

//    val host = "SQL8002.site4now.net"
//    val port = "1433"
////    val classes = "net.sourceforge.jtds.jdbc.Driver"
//    val database = "db_a963e4_quantunfcc"
//    val username = "db_a963e4_quantunfcc_admin"
//    val password = "Pratyushkr.123@"
////    val modelOutput: String
//
//
//    //    private val url = "jdbc:jtds:sqlserver://"+host+":"+port+"/"+database;
//    val url = "jdbc:jtds:sqlserver://$host:$port/$database"
//    val connection = DriverManager
//        .getConnection(url, username, password)
//
//    println(connection.isValid(0))
//
//
//    //     the query is only prepared not executed
//
//    val query =
//        connection.prepareStatement("SELECT Calories, Quantity FROM calories_dataset WHERE Name_of_Food = $id")
//
//    //     the query is executed and results are fetched
//    val result = query.executeQuery()
//    // an empty list for holding the results
//
//    val reception = mutableListOf<Reception>()
//
//    while (result.next()) {
//
//        val Quantity = result.getInt("Quantity")
//        val Calories = result.getString("Calories")
//    }



//fun recep(id: String?): MutableList<Reception> {
//    val host = "SQL8002.site4now.net"
//    val port = "1433"
//    val Classes = "net.sourceforge.jtds.jdbc.Driver"
//    val database = "db_a963e4_quantunfcc"
//    val username = "db_a963e4_quantunfcc_admin"
//    val password = "Pratyushkr.123@"
//
//
//    //    private val url = "jdbc:jtds:sqlserver://"+host+":"+port+"/"+database;
//    val url = "jdbc:jtds:sqlserver://$host:$port/$database"
//    val connection = DriverManager
//        .getConnection(url, username, password)
//
//    println(connection.isValid(0))
//
//
////     the query is only prepared not executed
//    val query =
//        connection.prepareStatement("SELECT Calories, Quantity FROM calories_dataset WHERE Name_of_Food = $id")
////     the query is executed and results are fetched
//    val result = query.executeQuery()
//    // an empty list for holding the results
//    val reception = mutableListOf<Reception>()
//
//    while (result.next()) {
//
//        val quantity = result.getInt("Quantity")
//        val calories = result.getInt("Calories")
//
//        reception.add(Reception(quantity, calories))
//
//    }
//    return reception
//}