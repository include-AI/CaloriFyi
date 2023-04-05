package com.example.calorifyi.RegistrationViewModel

import androidx.lifecycle.ViewModel

class LogisticsViewModel : ViewModel() {

    val instance = RegistrationViewModel()

//    For men: BMR = 88.362 + (13.397 x weight in kg) + (4.799 x height in cm) - (5.677 x age in years)
//    For women: BMR = 447.593 + (9.247 x weight in kg) + (3.098 x height in cm) - (4.330 x age in years)

    var a = if (instance.gender.value == "Male") 88.362 else 447.593
    var b = if (instance.gender.value == "Male") 13.397 else 9.247
    var bmr = a + (b * instance.weight.value.toDouble())
}