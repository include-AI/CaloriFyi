import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignUp_Additional( navController: NavController, viewModel: RegistrationViewModel) {
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

        val height_f_Options = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        var expanded_height_f by remember { mutableStateOf(false) }
        var height_f: String? by remember { mutableStateOf(viewModel.get_height_f()) }

        val height_i_Options =
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
        var expanded_height_i by remember { mutableStateOf(false) }
        var height_i: String? by remember { mutableStateOf(viewModel.get_height_i()) }

        val weight = remember { mutableStateOf(TextFieldValue(text = viewModel.getweight())) }
        val medicalInformation = remember { mutableStateOf(TextFieldValue()) }

        val goalOptions = arrayOf("Lose Weight", "Gain Weight", "Maintain Weight")
        var expanded_goal by remember { mutableStateOf(false) }
        var goal: String? by remember { mutableStateOf(viewModel.getgoal()) }

        val actOptions = arrayOf("Slightly active", "Moderately active", "Quite active")
        var expanded_act by remember { mutableStateOf(false) }
        var activity: String? by remember { mutableStateOf(viewModel.getactivity())}

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "3. Additional Information",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            fontFamily = googleSans,
            color = colorResource(
                id = R.color.usepurple
            )
        )

        Spacer(modifier = Modifier.height(30.dp))


        Row(modifier = Modifier.fillMaxWidth()) {
            ExposedDropdownMenuBox(
                expanded = expanded_height_f,
                onExpandedChange = { expanded_height_f = !expanded_height_f },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 30.dp)
                    .height(60.dp)
            ) {
                OutlinedTextField(
                    label = {
                        Text(
                            text = "Height (feets)" /*+ if (gender.value.text.isEmpty()) "*" else "",
                    color = if (lastName.value.text.isEmpty()) Color.Red else Color.Black */
                        )
                    },
                    value = height_f ?: "",
                    shape = RoundedCornerShape(50),
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded_height_f
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 30.dp)
                        .height(60.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded_height_f,
                    onDismissRequest = { expanded_height_f = false },
                ) {
                    height_f_Options.forEach { selectedOption ->
                        // menu item
                        DropdownMenuItem(onClick = {
                            height_f = selectedOption
                            expanded_height_f = false
                        }) {
                            Text(text = selectedOption)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(25.dp))
            ExposedDropdownMenuBox(
                expanded = expanded_height_i,
                onExpandedChange = { expanded_height_i = !expanded_height_i },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 30.dp)
                    .height(60.dp)
            ) {
                OutlinedTextField(
                    label = {
                        Text(
                            text = "Height (inches)" /*+ if (gender.value.text.isEmpty()) "*" else "",
                    color = if (lastName.value.text.isEmpty()) Color.Red else Color.Black */
                        )
                    },
                    value = height_i ?: "",
                    shape = RoundedCornerShape(50),
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded_height_i
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 30.dp)
                        .height(60.dp),
                )
                ExposedDropdownMenu(
                    expanded = expanded_height_i,
                    onDismissRequest = { expanded_height_i = false },
                ) {
                    height_i_Options.forEach { selectedOption ->
                        // menu item
                        DropdownMenuItem(onClick = {
                            height_i = selectedOption
                            expanded_height_i = false
                        }) {
                            Text(text = selectedOption)
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                label = {
                    Text(
                        text = "Weight (in kg)" /*+ if (weight.value.text.isEmpty()) "*" else "",
                    color = if (email.value.text.isEmpty()) Color.Red else Color.Black */
                    )
                },
                value = weight.value,
                onValueChange = { weight.value = it },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(60.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            ExposedDropdownMenuBox(
                expanded = expanded_goal,
                onExpandedChange = { expanded_goal = !expanded_goal }) {
                OutlinedTextField(
                    label = {
                        Text(
                            text = "What's your goal" /*+ if (gender.value.text.isEmpty()) "*" else "",
                    color = if (lastName.value.text.isEmpty()) Color.Red else Color.Black */
                        )
                    },
                    value = goal ?: "",
                    shape = RoundedCornerShape(50),
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded_goal
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 30.dp)
                        .height(60.dp),
                )
                ExposedDropdownMenu(
                    expanded = expanded_goal,
                    onDismissRequest = { expanded_goal = false }
                ) {
                    goalOptions.forEach { selectedOption ->
                        // menu item
                        DropdownMenuItem(onClick = {
                            goal = selectedOption
                            expanded_goal = false
                        }) {
                            Text(text = selectedOption)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ExposedDropdownMenuBox(
                expanded = expanded_act,
                onExpandedChange = { expanded_act = !expanded_act }) {
                OutlinedTextField(
                    label = {
                        Text(
                            text = "What's your daily activity status?" /*+ if (gender.value.text.isEmpty()) "*" else "",
                    color = if (lastName.value.text.isEmpty()) Color.Red else Color.Black */
                        )
                    },
                    value = activity ?: "",
                    shape = RoundedCornerShape(50),
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded_act
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 30.dp)
                        .height(60.dp),
                )
                ExposedDropdownMenu(
                    expanded = expanded_act,
                    onDismissRequest = { expanded_act = false }
                ) {
                    actOptions.forEach { selectedOption ->
                        // menu item
                        DropdownMenuItem(onClick = {
                            activity = selectedOption
                            expanded_act = false
                        }) {
                            Text(text = selectedOption)
                        }
                    }
                }
            }
        }





        Spacer(modifier = Modifier.height(45.dp))

        Row {
            Box(
                modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)
                    .weight(1f)
            ) {
                Button(
                    onClick = {
                        val height_i_value = height_i ?: ""
                        val height_f_value = height_f ?: ""
                        val goal_value = goal ?: ""
                        val activity_value = activity ?: ""
                        viewModel.setData3(
                            height_i_value,
                            height_f_value,
                            weight.value.text,
                            goal_value,
                            activity_value
                        )
                        navController.navigate(Screen.SignUpUser.route) },
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
                        if (height_i?.isEmpty() != false ||
                            height_f?.isEmpty() != false ||
                            weight.value.text.isEmpty() ||
                            goal?.isEmpty() != false ||
                            activity?.isEmpty() != false
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
                                val height_i_value = height_i ?: ""
                                val height_f_value = height_f ?: ""
                                val goal_value = goal ?: ""
                                val activity_value = activity ?: ""
                                viewModel.setData3(
                                    height_i_value,
                                    height_f_value,
                                    weight.value.text,
                                    goal_value,
                                    activity_value
                                )
                                val signup = signupAttempt3()
                                signup?.let {
                                    val query =
                                        "INSERT INTO user_info (FirstName, LastName, Gender, DateOfBirth, FoodPreferences, Username, Password, Email, PhoneNumber, fHeight, iHeight, Weight, Goal, Activity) VALUES ('${viewModel.getfirstName()}','${viewModel.getlastName()}','${viewModel.getGender()}','${viewModel.getDOB()}', '${viewModel.getPref()}','${viewModel.getusername()}','${viewModel.getpassword()}','${viewModel.getemail()}','${viewModel.getphone()}','${viewModel.get_height_f()}','${viewModel.get_height_i()}','${viewModel.getweight()}','${viewModel.getgoal()}','${viewModel.getactivity()}')"
                                    val statement = signup.createStatement()
                                    val rowsAffected = statement.executeUpdate(query)
                                    if (rowsAffected > 0) {
                                        withContext(Dispatchers.Main) {
                                            isLoading.value = false
                                            navController.navigate(Screen.LogIn.route)
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Choose a unique username",
                                            Toast.LENGTH_SHORT
                                        ).show()
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
                    } else { }
                    Text(text = ">", color = Color.White, fontFamily = googleSans)
                }
            }

//        Box(
//            modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)
//        ){
//            Button(
//                onClick = {
//                    if (height_i?.isEmpty() != false ||
//                        height_f?.isEmpty() != false ||
//                        weight.value.text.isEmpty() || goal?.isEmpty() != false
//                    ) {
//                        Toast.makeText(
//                            context,
//                            "Please enter the mandatory fields",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        isLoading.value = true
//                        val scope = CoroutineScope(Dispatchers.IO)
//                        scope.launch {
//                            val height_i_value = height_i ?:""
//                            val height_f_value = height_f ?:""
//                            val goal_value = goal ?: ""
//                            viewModel.setData3(height_i_value, height_f_value, weight.value.text, goal_value)
//                            val signup = signupAttempt3()
//                            signup?.let {
////
//////                            val resultSet = statement?.executeUpdate()
////                                if (rowsAffected > 0) {
////                                    withContext(Dispatchers.Main) {
////                                        isLoading.value = false
////                                        navController.navigate(Screen.SignUp_User.route)
////                                    }
////                                } else {
////                                    // insertion failed
////                                }
//                                val query =
//                                    "INSERT INTO user_info (FirstName, LastName, Gender, DateOfBirth, FoodPreferences, Username, Password, Email, PhoneNumber, fHeight, iHeight, Weight, Goal) VALUES ('${viewModel.getfirstName()}','${viewModel.getlastName()}','${viewModel.getGender()}','${viewModel.getDOB()}', '${viewModel.getPref()}','${viewModel.getusername()}','${viewModel.getpassword()}','${viewModel.getemail()}','${viewModel.getphone()}','${viewModel.get_height_f()}','${viewModel.get_height_i()}','${viewModel.getweight()}','${viewModel.getgoal()}')"
//                                val statement = signup.createStatement()
//                                val rowsAffected = statement.executeUpdate(query)
//                                if (rowsAffected > 0) {
//                                    withContext(Dispatchers.Main) {
//                                        isLoading.value = false
//                                        navController.navigate(Screen.LogIn.route)
//                                    }
//                                } else {
//                                    Toast.makeText(
//                                        context,
//                                        "Choose a unique username",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            }
//                        }
//                    }
//                },
//                shape = RoundedCornerShape(50.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//
//            ) {
//                if (isLoading.value) { // If the flag is true, show the progress indicator
//                    CircularProgressIndicator(color = Color.White)
//                }
//                else{}
//                Text(text = "Sign Up", color = Color.White, fontFamily = googleSans)
//            }
//        }
        }
    }
}

fun signupAttempt3(): Connection? {
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
