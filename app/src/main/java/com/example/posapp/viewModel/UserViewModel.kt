package com.example.posapp.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.posapp.db.User.User
import com.example.posapp.db.User.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val inputEmail = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()

    var getUserLogin = MutableLiveData<Boolean>()
    var inputEmpty = MutableLiveData<String>()
    var inputError = MutableLiveData<String>()

    init {
        inputPassword.value = ""
        inputEmail.value = ""
        getUserLogin.value = false
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun authorize() {
        val email = inputEmail.value!!
        val password = inputPassword.value!!
        if (email.isEmpty() || password.isEmpty()) {
            inputEmpty.value = "Email or password is empty"
        } else {
        userLogin(email, password)}
    }

    private fun userLogin(email: String, password: String) = runBlocking {
        val user = repository.userLogin(email, password)
        if (user != null) {
            getUserLogin.value = true
        }else{
            inputError.value = "Email or password is wrong"
        }
    }
}