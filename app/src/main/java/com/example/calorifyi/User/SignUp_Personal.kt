import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.End
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.calorifyi.Navigation.Screen
import com.example.calorifyi.R
import com.example.calorifyi.RegistrationViewModel.RegistrationViewModel
import com.example.calorifyi.ui.theme.googleSans
import com.example.calorifyi.ui.theme.onb
import java.util.*
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignUp_Personal(navController: NavController, viewModel: RegistrationViewModel) {
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
        ){
            Image(
                painter = painterResource(id = R.drawable.signup),
                contentDescription = "Banner",
                modifier = Modifier
                    .fillMaxSize())

        }

        val context = LocalContext.current

        var firstName = remember { mutableStateOf(TextFieldValue(text = viewModel.getfirstName())) }
        var lastName = remember { mutableStateOf(TextFieldValue(text = viewModel.getlastName())) }

        val genderOptions = arrayOf("Male", "Female", "Non-Binary")
        var expanded_gender by remember { mutableStateOf(false) }
        var gender: String? by remember { mutableStateOf(viewModel.getGender()) }

        val foodPreferences = arrayOf("Vegetarian", "Non-Vegetarian", "Vegan")
        var expanded_fp by remember { mutableStateOf(false) }
        var pref: String? by remember { mutableStateOf(viewModel.getPref()) }

        val mYear: Int
        val mMonth: Int
        val mDay: Int
        val now = Calendar.getInstance()
        mYear = now.get(Calendar.YEAR)
        mMonth = now.get(Calendar.MONTH)
        mDay = now.get(Calendar.DAY_OF_MONTH)
        now.time = Date()
        val date = remember{ mutableStateOf("${viewModel.getDOB()}") }

        fun getFormattedDate(date: Date, format: String): String {
            val formatter = SimpleDateFormat(format)
            return formatter.format(date)
        }

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val cal = Calendar.getInstance()
                cal.set(year, month, dayOfMonth)
                date.value = getFormattedDate(cal.time, "dd MMM,yyy")
            }, mYear, mMonth, mDay
        )
        val maxDate = now.timeInMillis // maximum date is the current date
        now.add(Calendar.YEAR, -100) // subtract 5 years from the current date
        val minDate = now.timeInMillis

        datePickerDialog.datePicker.minDate = minDate
        datePickerDialog.datePicker.maxDate = maxDate

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "1. General Information", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold), fontFamily = googleSans, color = colorResource(
            id = R.color.usepurple))

        Spacer(modifier = Modifier.height(30.dp))


        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                label = { Text(text = "First Name" /*+ if (firstName.value.text.isEmpty()) "*" else "",
                    color = if (firstName.value.text.isEmpty()) Color.Red else Color.Black*/)},
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
                label = { Text(text = "Last Name" /*+ if (lastName.value.text.isEmpty()) "*" else "",
                    color = if (lastName.value.text.isEmpty()) Color.Red else Color.Black */)},
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



            ExposedDropdownMenuBox(expanded = expanded_gender, onExpandedChange = {expanded_gender = !expanded_gender}) {
                OutlinedTextField(
                label = { Text(text = "Select Gender" /*+ if (gender.value.text.isEmpty()) "*" else "",
                    color = if (lastName.value.text.isEmpty()) Color.Red else Color.Black */)},
                value = gender ?: "",
                shape = RoundedCornerShape(50),
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded_gender
                        )},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(60.dp),
            )
                ExposedDropdownMenu(
                    expanded = expanded_gender,
                    onDismissRequest = { expanded_gender = false }
                ) {
                    genderOptions.forEach { selectedOption ->
                        // menu item
                        DropdownMenuItem(onClick = {
                            gender = selectedOption
                            expanded_gender = false
                        }) {
                            Text(text = selectedOption)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ExposedDropdownMenuBox(expanded = expanded_fp, onExpandedChange = {expanded_fp = !expanded_fp}) {
                OutlinedTextField(
                    label = { Text(text = "Food Prefernce" /*+ if (gender.value.text.isEmpty()) "*" else "",
                    color = if (lastName.value.text.isEmpty()) Color.Red else Color.Black */)},
                    value = pref ?:"",
                    shape = RoundedCornerShape(50),
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded_fp
                        )},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 30.dp)
                        .height(60.dp),
                )
                ExposedDropdownMenu(
                    expanded = expanded_fp,
                    onDismissRequest = { expanded_fp = false }
                ) {
                    foodPreferences.forEach { selectedOption ->
                        // menu item
                        DropdownMenuItem(onClick = {
                            pref = selectedOption
                            expanded_fp = false
                        }) {
                            Text(text = selectedOption)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                label = { Text(text = "Enter DOB: " /*+ if (firstName.value.text.isEmpty()) "*" else "",
                    color = if (firstName.value.text.isEmpty()) Color.Red else Color.Black*/)},
                trailingIcon = { IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "calendar",
                        modifier = Modifier.size(25.dp)
                    )
                }},
                value = date.value,
                readOnly = true,
                shape = RoundedCornerShape(50),
                onValueChange = { date.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(60.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

        }

        Spacer(modifier = Modifier.height(45.dp))

        Box(
            modifier = Modifier
                .padding(40.dp, 0.dp, 40.dp, 0.dp)
                .align(End)
        ){
            Button(
                onClick = {
                    if (firstName.value.text.isEmpty() ||
                        lastName.value.text.isEmpty() ||
                        gender?.isEmpty() != false ||
                        date.value.isEmpty() ||
                        pref?.isEmpty() != false
                            ) {
                        Toast.makeText(
                            context,
                            "Please enter the mandatory fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val genderValue = gender ?: ""
                        val prefValue = pref ?: ""
                        viewModel.setData1(firstName.value.text, lastName.value.text, genderValue, prefValue,date.value)
                        navController.navigate(Screen.SignUpUser.route)
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .width(120.dp)
                    .height(50.dp)

            ) {
                Text(text = "Next", color = Color.White, fontFamily = googleSans)
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

//fun signupAttempt(): Connection? {
//    val host = "SQL8002.site4now.net"
//    val port = "1433"
//    val database = "db_a963e4_quantunfcc"
//    val admin_username = "db_a963e4_quantunfcc_admin"
//    val admin_password = "Pratyushkr.123@"
//    val url = "jdbc:jtds:sqlserver://$host:$port/$database"
//
//    return try {
//        Class.forName("net.sourceforge.jtds.jdbc.Driver")
//        DriverManager.getConnection(url, admin_username, admin_password)
//    } catch (e: Exception) {
//        null
//    }
//}

//@Composable
//fun SignUp( navController: NavController ) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(onb)
//    ) {
////        ClickableText(
////            text = AnnotatedString("Already registered? LogIn"),
////            onClick = {
////                navController.navigate(Screen.LogIn.route)
////            },
////            modifier = Modifier
////                .align(Alignment.BottomCenter)
////                .padding(20.dp),
////            style = TextStyle(
////                fontSize = 18.sp,
////                textDecoration = TextDecoration.Underline,
////                color = colorResource(id = R.color.usepurple)
////            )
////        )
//    }
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp)
//                .padding(top = 10.dp)
//        ){
//            Image(
//                painter = painterResource(id = R.drawable.signup),
//                contentDescription = "Banner",
//                modifier = Modifier
//                    .fillMaxSize())
//
//        }
//        val context = LocalContext.current
//        val isLoading = remember { mutableStateOf(false) }
//        val firstName = remember { mutableStateOf(TextFieldValue()) }
//        val lastName = remember { mutableStateOf(TextFieldValue()) }
//        val username = remember { mutableStateOf(TextFieldValue()) }
//        val password = remember { mutableStateOf(TextFieldValue()) }
//        val email = remember { mutableStateOf(TextFieldValue()) }
//
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        Text(text = "1. General Information", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold), fontFamily = googleSans, color = colorResource(
//            id = R.color.usepurple))
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//
//        Row(modifier = Modifier.fillMaxWidth()) {
//            OutlinedTextField(
//                label = { Text(text = "First Name" + if (firstName.value.text.isEmpty()) "*" else "",
//                    color = if (firstName.value.text.isEmpty()) Color.Red else Color.Black)},
//                value = firstName.value,
//                shape = RoundedCornerShape(50),
//                onValueChange = { firstName.value = it },
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(start = 30.dp)
//                    .height(60.dp)
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            OutlinedTextField(
//                label = { Text(text = "Last Name" + if (lastName.value.text.isEmpty()) "*" else "",
//                    color = if (lastName.value.text.isEmpty()) Color.Red else Color.Black )},
//                value = lastName.value,
//                shape = RoundedCornerShape(50),
//                onValueChange = { lastName.value = it },
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(end = 30.dp)
//                    .height(60.dp)
//            )
//        }
//        Column(modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 30.dp)){
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            OutlinedTextField(
//                label = { Text(text = "Username" + if (username.value.text.isEmpty()) "*" else "",
//                    color = if (username.value.text.isEmpty()) Color.Red else Color.Black )},
//                value = username.value,
//                shape = RoundedCornerShape(50),
//                onValueChange = {username.value = it},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(end = 30.dp)
//                    .height(60.dp))
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            OutlinedTextField(
//                label = { Text(text = "Email" + if (email.value.text.isEmpty()) "*" else "",
//                    color = if (email.value.text.isEmpty()) Color.Red else Color.Black )},
//                value = email.value,
//                onValueChange = {email.value = it},
//                shape = RoundedCornerShape(50),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(end = 30.dp)
//                    .height(60.dp))
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            OutlinedTextField(
//                label = { Text(text = "Password" + if (password.value.text.isEmpty()) "*" else "",
//                    color = if (password.value.text.isEmpty()) Color.Red else Color.Black )},
//                value = password.value,
//                shape = RoundedCornerShape(50),
//                visualTransformation = PasswordVisualTransformation(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//                onValueChange = {password.value = it},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(end = 30.dp)
//                    .height(60.dp)
//            )
//        }
//
//
//
//
//
//        Spacer(modifier = Modifier.height(45.dp))
//
//        Box(
//            modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)
//                .align(End)
//        ){
//            Button(
//                onClick = {
//                    if (firstName.value.text.isEmpty() ||
//                        lastName.value.text.isEmpty() ||
//                        username.value.text.isEmpty() ||
//                        email.value.text.isEmpty() ||
//                        password.value.text.isEmpty()
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
//                            val signup = signupAttempt()
//                            signup?.let {
//                                val query =
//                                    "INSERT INTO user_info (FirstName, LastName, Username, Email, Password) VALUES ('${firstName.value.text}','${lastName.value.text}', '${username.value.text}', '${email.value.text}','${password.value.text}')"
//                                val statement = signup.createStatement()
//                                val rowsAffected = statement.executeUpdate(query)
////
////                            val resultSet = statement?.executeUpdate()
//                                if (rowsAffected > 0) {
//                                    withContext(Dispatchers.Main) {
//                                        isLoading.value = false
//                                        navController.navigate(Screen.SignUp_User.route)
//                                    }
//                                } else {
//                                    // insertion failed
//                                }
//                            }
//                        }
//                    }
//                },
//                shape = RoundedCornerShape(50.dp),
//                modifier = Modifier
//                    .width(120.dp)
//                    .height(50.dp)
//
//            ) {
//                if (isLoading.value) { // If the flag is true, show the progress indicator
//                    CircularProgressIndicator(color = Color.White)
//                }
//                else{}
//                Text(text = "Next", color = Color.White, fontFamily = googleSans)
//            }
//        }
//        Spacer(modifier = Modifier.height(40.dp))
//    }
//}
//
//fun signupAttempt(): Connection? {
//    val host = "SQL8002.site4now.net"
//    val port = "1433"
//    val database = "db_a963e4_quantunfcc"
//    val admin_username = "db_a963e4_quantunfcc_admin"
//    val admin_password = "Pratyushkr.123@"
//    val url = "jdbc:jtds:sqlserver://$host:$port/$database"
//
//    return try {
//        Class.forName("net.sourceforge.jtds.jdbc.Driver")
//        DriverManager.getConnection(url, admin_username, admin_password)
//    } catch (e: Exception) {
//        null
//    }
//}
//
//@Preview
//@Composable
//fun SignUpPreview() {
//    val navController = rememberNavController()
//    SignUp(navController = navController)
//}