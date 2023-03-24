//package com.example.calorifyi.Navigation
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.navigation.compose.rememberNavController
//import com.example.calorifyi.ui.theme.CaloriFyiTheme
//import java.sql.DriverManager
//
////receiving objects
//
//data class Reception(val Quantity: Int, val Calories: Int)
//
//class ReceptionActivity: ComponentActivity(){
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            CaloriFyiTheme {
//                var modelOutput = intent.getStringExtra("query_image")
//                CalorieDisplay(modelOutput)
//            }
//        }
//    }
//}
//
//
//@Composable
//fun CalorieDisplay(id: String?){
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "$id")
//    }
//
//
//}
//
//
//
//
////function
//
//fun recep(){
//    val host = "SQL8002.site4now.net"
//    val port = "1433"
//    val Classes = "net.sourceforge.jtds.jdbc.Driver"
//    val database = "db_a963e4_quantunfcc"
//    val username = "db_a963e4_quantunfcc_admin"
//    val password = "Pratyushkr.123@"
//    val modelOutput: String
//
//
//    //    private val url = "jdbc:jtds:sqlserver://"+host+":"+port+"/"+database;
//    val url = "jdbc:jtds:sqlserver://$host:$port/$database"
//    val connection = DriverManager
//        .getConnection(url, "$username", "$password")
//
//    println(connection.isValid(0))
//
//
//
////     the query is only prepared not executed
//    val query = connection.prepareStatement("SELECT Calories, Quantity FROM calories_dataset WHERE Name_of_Food = $modelOutput")
////     the query is executed and results are fetched
//    val result = query.executeQuery()
//    // an empty list for holding the results
//    val reception = mutableListOf<Reception>()
//
//    while(result.next()){
//
//        val Quantity = result.getInt("Quantity")
//        val Calories = result.getString("Calories")
//    }
//}
