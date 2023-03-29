package com.example.calorifyi.User


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.calorifyi.Navigation.Screen
import com.example.calorifyi.R

import com.example.calorifyi.ui.theme.onb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogIn(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(onb)
    ) {
        ClickableText(
            text = AnnotatedString("Click here to Sign Up"),
            onClick = {
                navController.navigate(Screen.SignUp.route)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            style = TextStyle(
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline,
                color = colorResource(id = R.color.usepurple)
            )
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current
        val isLoading = remember { mutableStateOf(false) }
        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        Text(text = "Login", style = TextStyle(fontSize = 40.sp))

        Spacer(modifier = Modifier.height(50.dp))

        OutlinedTextField(
            label = {Text(text = "Username")},
            value = username.value,
            shape = RoundedCornerShape(50),
            onValueChange = {username.value = it})

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            label = {Text(text = "Password")},
            value = password.value,
            shape = RoundedCornerShape(50),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = {password.value = it})

        Spacer(modifier = Modifier.height(35.dp))

        Box(
            modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)
        ){
            Button(
                onClick = {
                    isLoading.value = true
                    val scope = CoroutineScope(Dispatchers.IO)
                    scope.launch {
                        val login = loginAttempt()
                        login?.let {
                            val query =
                                "SELECT COUNT(*) FROM user_info WHERE Username = '${username.value.text}' AND Password = '${password.value.text}'"
                            val statement = login.createStatement()
                            val resultSet = statement.executeQuery(query)
                            resultSet.next()
                            val count = resultSet.getInt(1)
                            if (count > 0) {
                                withContext(Dispatchers.Main) {
                                    isLoading.value = false
                                    navController.navigate(Screen.AppView.route)
                                }
                            } else {
                                withContext(Dispatchers.Main){Toast.makeText(
                                    context,
                                    "Credentials did not match",
                                    Toast.LENGTH_SHORT).show()
                                    isLoading.value = false
                                }
                            }
                            resultSet.close()
                            statement.close()
                            login.close()
                        }
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
//                    val login = loginAttempt()
//                    login?.let {
//                        val query =
//                            "SELECT COUNT(*) FROM User_Info WHERE Username = '$username' AND Password = '$password'"
//                        val statement = login.createStatement()
//                        val resultSet = statement.executeQuery(query)
//                        resultSet.next()
//                        val count = resultSet.getInt(1)
//                        if (count > 0) {
//                            navController.navigate(Screen.Home.route)
//                        } else {
//
//                        }
//                        resultSet.close()
//                        statement.close()
//                        login.close()
//                    }
//                },
//                shape = RoundedCornerShape(50.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
            ) {
                if (isLoading.value) { // If the flag is true, show the progress indicator
                    CircularProgressIndicator(color = Color.White)}
                else{}
                Text(text = "Login", color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = { },
            style = TextStyle(
                fontSize = 14.sp
            )
        )
    }
}

fun loginAttempt(): Connection? {
    val host = "SQL8002.site4now.net"
    val port = "1433"
    val database = "db_a963e4_quantunfcc"
    val admin_username = "db_a963e4_quantunfcc_admin"
    val admin_password = "Pratyushkr.123@"
    val url = "jdbc:jtds:sqlserver://$host:$port/$database"
//    val connection = DriverManager
//        .getConnection(url, username, password)

    return try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        DriverManager.getConnection(url, admin_username, admin_password)
    } catch (e: Exception) {
        null
    }
}

@Preview
@Composable
fun LogInPreview() {
    val navController = rememberNavController()
    LogIn(navController = navController)
}