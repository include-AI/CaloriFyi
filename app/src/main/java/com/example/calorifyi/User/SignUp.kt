import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calorifyi.BottomMenu
import com.example.calorifyi.Navigation.BottomMenuContent
import com.example.calorifyi.Navigation.Screen
import com.example.calorifyi.R
import com.example.calorifyi.ui.theme.CaloriFyiTheme
import com.example.calorifyi.ui.theme.googleSans
import com.example.calorifyi.ui.theme.onb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager

@Composable
fun SignUp( navController: NavController ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(onb)
    ) {
//        ClickableText(
//            text = AnnotatedString("Already registered? LogIn"),
//            onClick = {
//                navController.navigate(Screen.LogIn.route)
//            },
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(20.dp),
//            style = TextStyle(
//                fontSize = 18.sp,
//                textDecoration = TextDecoration.Underline,
//                color = colorResource(id = R.color.usepurple)
//            )
//        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(top = 10.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.signup),
                contentDescription = "Banner",
                modifier = Modifier
                    .fillMaxSize())

        }
        val context = LocalContext.current
        val isLoading = remember { mutableStateOf(false) }
        val firstName = remember { mutableStateOf(TextFieldValue()) }
        val lastName = remember { mutableStateOf(TextFieldValue()) }
        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }


        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "1. General Information", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold), fontFamily = googleSans, color = colorResource(
            id = R.color.usepurple))

        Spacer(modifier = Modifier.height(30.dp))


        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                label = { Text(text = "First Name" + if (firstName.value.text.isEmpty()) "*" else "",
                    color = if (firstName.value.text.isEmpty()) Color.Red else Color.Black)},
                value = firstName.value,
                shape = RoundedCornerShape(50),
                onValueChange = { firstName.value = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 30.dp)
                    .height(60.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                label = { Text(text = "Last Name" + if (lastName.value.text.isEmpty()) "*" else "",
                    color = if (lastName.value.text.isEmpty()) Color.Red else Color.Black )},
                value = lastName.value,
                shape = RoundedCornerShape(50),
                onValueChange = { lastName.value = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 30.dp)
                    .height(60.dp)
            )
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp)){

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                label = { Text(text = "Username" + if (username.value.text.isEmpty()) "*" else "",
                    color = if (username.value.text.isEmpty()) Color.Red else Color.Black )},
                value = username.value,
                shape = RoundedCornerShape(50),
                onValueChange = {username.value = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(60.dp))

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                label = { Text(text = "Email" + if (email.value.text.isEmpty()) "*" else "",
                    color = if (email.value.text.isEmpty()) Color.Red else Color.Black )},
                value = email.value,
                onValueChange = {email.value = it},
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(60.dp))

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                label = { Text(text = "Password" + if (password.value.text.isEmpty()) "*" else "",
                    color = if (password.value.text.isEmpty()) Color.Red else Color.Black )},
                value = password.value,
                shape = RoundedCornerShape(50),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = {password.value = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(60.dp)
            )
        }





        Spacer(modifier = Modifier.height(45.dp))

        Box(
            modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)
        ){
            Button(
                onClick = {
                    if (firstName.value.text.isEmpty() ||
                        lastName.value.text.isEmpty() ||
                        username.value.text.isEmpty() ||
                        email.value.text.isEmpty() ||
                        password.value.text.isEmpty()
                    ) {
                        Toast.makeText(
                            context,
                            "Pehle entry daal tb hi hoga signup",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        isLoading.value = true
                        val scope = CoroutineScope(Dispatchers.IO)
                        scope.launch {
                            val signup = signupAttempt()
                            signup?.let {
                                val query =
                                    "INSERT INTO user_info (FirstName, LastName, Username, Email, Password) VALUES ('${firstName.value.text}','${lastName.value.text}', '${username.value.text}', '${email.value.text}','${password.value.text}')"
                                val statement = signup.createStatement()
                                val rowsAffected = statement.executeUpdate(query)
//                            val query = "INSERT INTO user_info (FirstName, LastName, Username, Email, Password) VALUES (?, ?, ?, ?, ?)"
//                            val statement = Connection?.prepareStatement(query)
//                            statement?.setString(1, firstName.value.text)
//                            statement?.setString(2, lastName.value.text)
//                            statement?.setString(3, username.value.text)
//                            statement?.setString(4, email.value.text)
//                            statement?.setString(5, password.value.text)
//                            val resultSet = statement?.executeUpdate()
                                if (rowsAffected > 0) {
                                    withContext(Dispatchers.Main) {
                                        isLoading.value = false
                                        navController.navigate(Screen.LogIn.route)
                                    }
                                } else {
                                    // insertion failed
                                }
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)

            ) {
                if (isLoading.value) { // If the flag is true, show the progress indicator
                    CircularProgressIndicator(color = Color.White)
                }
                else{}
                Text(text = "Sign Up", color = Color.White, fontFamily = googleSans)
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

fun signupAttempt(): Connection? {
    val host = "SQL8002.site4now.net"
    val port = "1433"
    val database = "db_a963e4_quantunfcc"
    val admin_username = "db_a963e4_quantunfcc_admin"
    val admin_password = "Pratyushkr.123@"
    val url = "jdbc:jtds:sqlserver://$host:$port/$database"

    return try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        DriverManager.getConnection(url, admin_username, admin_password)
    } catch (e: Exception) {
        null
    }
}

@Preview
@Composable
fun SignUpPreview() {
    val navController = rememberNavController()
    SignUp(navController = navController)
}