package com.example.calorifyi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calorifyi.ui.theme.CalorificatorTheme


@Composable
fun LoginScreen() {
    var emailText by remember {
        mutableStateOf(TextFieldValue())
    }
    var emailErrorState by remember {
        mutableStateOf(false)
    }
    var passwordText by remember {
        mutableStateOf(TextFieldValue())
    }
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            modifier = Modifier
                .width(300.dp),
            singleLine = true,
            value = emailText,
            shape = RoundedCornerShape(50),
            label = {
                    Text(
                        text = "Email",
                        color = Color.Black)
            },
            placeholder = {
                Text(
                    text = "Enter Your Email Address",
                    color = Color.Black)
            },
            onValueChange = { newText ->
                emailText = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .width(300.dp),
            singleLine = true,
            value = passwordText,
            shape = RoundedCornerShape(50),
            label = {
                    Text(
                        text = "Password",
                        color = Color.Black)
            },
            placeholder = {
                Text(
                    text = "Enter Your Password",
                    color = Color.Black)
            },
            onValueChange = { newText ->
                passwordText = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
    }

}

@Preview
@Composable
fun LoginScreenPreview(){
    CalorificatorTheme {
        LoginScreen()
    }
}