package com.example.calorifyi

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.calorifyi.Navigation.Screen
import com.example.calorifyi.ui.theme.UsePurple

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape)
        )
        Spacer(modifier = Modifier.height(100.dp))
        Spacer(modifier = Modifier.height(80.dp))
        Button(
            onClick = {
                navController.navigate(Screen.Onboarding.route)
            },
            modifier = Modifier
                .width(200.dp)
                .height(45.dp),
            border = BorderStroke(1.dp, Color.Transparent),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = UsePurple)
            ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {  },
            modifier = Modifier
                .width(200.dp)
                .height(45.dp),
            border = BorderStroke(1.dp, Color.Transparent),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = UsePurple)
        ) {
            Text(text = "Sign Up")
        }
    }
}

