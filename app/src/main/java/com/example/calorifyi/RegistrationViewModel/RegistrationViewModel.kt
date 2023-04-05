package com.example.calorifyi.RegistrationViewModel


import androidx.compose.runtime.State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegistrationViewModel: ViewModel() {

    private val _fname: MutableState<String> = mutableStateOf("")
    private val _lname: MutableState<String> = mutableStateOf("")
    private val _gender: MutableState<String> = mutableStateOf("")
    private val _pref: MutableState<String> = mutableStateOf("")
    private val _date: MutableState<String> = mutableStateOf("")

    var firstName: State<String> = _fname
    val lastName: State<String> = _lname
    val gender: State<String> = _gender
    val pref: State<String> = _pref
    val date: State<String> = _date

    fun setData1(fname: String, lname: String, gender: String, pref: String, date: String){
        _fname.value = fname
        _lname.value = lname
        _gender.value = gender
        _pref.value = pref
        _date.value = date}

    fun getfirstName(): String {return firstName.value}
    fun getlastName(): String {return lastName.value}
    fun getGender(): String {return gender.value}
    fun getPref(): String {return pref.value}
    fun getDOB(): String {return date.value}

    private val _email: MutableState<String> = mutableStateOf("")
    private val _phone: MutableState<String> = mutableStateOf("")
    private val _password: MutableState<String> = mutableStateOf("")
    private val _username: MutableState<String> = mutableStateOf("")

    val email: State<String> = _email
    val password: State<String> = _password
    val phone: State<String> = _phone
    val username: State<String> = _username

    fun setData2(email: String, password: String, phone: String, username: String){
        _email.value = email
        _password.value = password
        _phone.value = phone
        _username.value = username}

    fun getemail(): String {return email.value}
    fun getphone(): String {return phone.value}
    fun getpassword(): String {return password.value}
    fun getusername(): String {return username.value}

    private val _iHeight: MutableState<String> = mutableStateOf("")
    private val _fHeight: MutableState<String> = mutableStateOf("")
    private val _weight: MutableState<String> = mutableStateOf("")
    private val _goal: MutableState<String> = mutableStateOf("")
    private val _activity: MutableState<String> = mutableStateOf("")

    val height_i: State<String> = _iHeight
    val height_f: State<String> = _fHeight
    val weight: State<String> = _weight
    val goal: State<String> = _goal
    val activity: State<String> = _activity

    fun setData3(height_i: String, height_f: String, weight: String, goal: String, activity: String){
        _iHeight.value = height_i
        _fHeight.value = height_f
        _weight.value = weight
        _goal.value = goal
        _activity.value = activity}

    fun get_height_i(): String {return height_i.value}
    fun get_height_f(): String {return height_f.value}
    fun getweight(): String {return weight.value}
    fun getgoal(): String {return goal.value}
    fun getactivity(): String {return activity.value}
}