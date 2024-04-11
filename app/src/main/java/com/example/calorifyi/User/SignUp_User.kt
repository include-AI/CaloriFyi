import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calorifyi.Navigation.Screen
import com.example.calorifyi.R
import com.example.calorifyi.RegistrationViewModel.RegistrationViewModel
import com.example.calorifyi.ui.theme.googleSans
import com.example.calorifyi.ui.theme.onb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager

@Composable
fun SignUp_User(navController: NavController, viewModel: RegistrationViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(onb)
    ) {
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
        ) {
            Image(
                painter = painterResource(id = R.drawable.signup),
                contentDescription = "Banner",
                modifier = Modifier
                    .fillMaxSize()
            )

        }

        val context = LocalContext.current
        val isLoading = remember { mutableStateOf(false) }

        val email = remember { mutableStateOf(TextFieldValue(text = viewModel.getemail())) }
        val phoneNumber = remember { mutableStateOf(TextFieldValue(text = viewModel.getphone())) }
        val username = remember { mutableStateOf(TextFieldValue(text = viewModel.getusername())) }
        val password = remember { mutableStateOf(TextFieldValue(text = viewModel.getpassword())) }



        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "2. User Information",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            fontFamily = googleSans,
            color = colorResource(
                id = R.color.usepurple
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                label = {
                    Text(
                        text = "Email" /*+ if (email.value.text.isEmpty()) "*" else "",
                        color = if (username.value.text.isEmpty()) Color.Red else Color.Black*/
                    )
                },
                value = email.value,
                shape = RoundedCornerShape(50),
                onValueChange = { email.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(60.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                label = {
                    Text(
                        text = "Phone Number" /*+ if (email.value.text.isEmpty()) "*" else "",
                        color = if (email.value.text.isEmpty()) Color.Red else Color.Black*/
                    )
                },
                value = phoneNumber.value,
                onValueChange = { phoneNumber.value = it },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(60.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                label = {
                    Text(
                        text = "Username" /*+ if (email.value.text.isEmpty()) "*" else "",
                        color = if (email.value.text.isEmpty()) Color.Red else Color.Black*/
                    )
                },
                value = username.value,
                onValueChange = { username.value = it },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(60.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                label = {
                    Text(
                        text = "Password" /*+ if (password.value.text.isEmpty()) "*" else "",
                        color = if (password.value.text.isEmpty()) Color.Red else Color.Black*/
                    )
                },
                value = password.value,
                shape = RoundedCornerShape(50),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(45.dp))
        Row {
            Box(
                modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)
                    .weight(1f)
            ) {
                Button(
                    onClick = { navController.navigate(Screen.SignUpPersonal.route) },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)
                ) {
                    Text(text = "Prev", color = Color.White, fontFamily = googleSans)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
            Box(
                modifier = Modifier
                    .padding(40.dp, 0.dp, 40.dp, 0.dp)
                    .weight(1f)
            ) {
                Button(
                    onClick = {
                        if (email.value.text.isEmpty() ||
                            phoneNumber.value.text.isEmpty() ||
                            username.value.text.isEmpty() ||
                            password.value.text.isEmpty()
                        ) {
                            Toast.makeText(
                                context,
                                "Please enter the mandatory fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            isLoading.value = true
                            val scope = CoroutineScope(Dispatchers.IO)
                            scope.launch {
                                val signup = signupAttempt2()
                                signup?.let {
                                    val query =
                                        "SELECT COUNT(*) FROM user_info WHERE Username = '${username.value.text}'"
                                    val statement = signup.createStatement()
                                    val resultSet = statement.executeQuery(query)
                                    resultSet.next()
                                    val count = resultSet.getInt(1)
                                    if (count > 0) {
                                        withContext(Dispatchers.Main) {
                                            isLoading.value = false
                                            Toast.makeText(
                                                context,
                                                "Choose a unique username",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        withContext(Dispatchers.Main) {
                                            viewModel.setData2(email.value.text, password.value.text, phoneNumber.value.text, username.value.text)
                                            navController.navigate(Screen.SignUpAdditional.route)
                                        }
                                    }
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp)

                ) {
                    if (isLoading.value) { // If the flag is true, show the progress indicator
                        CircularProgressIndicator(color = Color.White)
                    } else {
                    }
                    Text(text = "Next", color = Color.White, fontFamily = googleSans)
                }
            }
        }
    }
}

fun signupAttempt2(): Connection? {
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
