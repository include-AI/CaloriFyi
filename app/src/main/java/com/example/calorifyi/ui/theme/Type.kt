package com.example.calorifyi.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.calorifyi.R

// Set of Material typography styles to start with
val roboto = FontFamily(
    Font(R.font.roboto_regular),
    Font(R.font.roboto_bold),
    Font(R.font.roboto_black),
    Font(R.font.roboto_blackitalic),
    Font(R.font.roboto_light),
    Font(R.font.roboto_italic),
    Font(R.font.roboto_medium),
    Font(R.font.roboto_mediumitalic),
    Font(R.font.roboto_thin),
    Font(R.font.roboto_thinitalic)
)

val googleSans = FontFamily(
    Font(R.font.productsans_regular),
    Font(R.font.productsans_bold),
    Font(R.font.productsans_bolditalic),
    Font(R.font.productsans_black),
    Font(R.font.productsans_blackitalic),
    Font(R.font.productsans_light),
    Font(R.font.productsans_lightitalic),
    Font(R.font.productsans_italic),
    Font(R.font.productsans_medium),
    Font(R.font.productsans_mediumitalic),
    Font(R.font.productsans_thin),
    Font(R.font.productsans_thinitalic)
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = googleSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontFamily = googleSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)