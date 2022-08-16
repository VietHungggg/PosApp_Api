package com.example.posapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.posapp.databinding.ActivityLoginBinding
import com.example.posapp.databinding.ActivitySingupBinding
import com.example.posapp.db.MealDatabase
import com.example.posapp.db.User.User
import com.example.posapp.db.User.UserRepository
import com.example.posapp.viewModel.UserViewModel
import com.example.posapp.viewModel.UserViewModelFactory

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingupBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDao = MealDatabase.getInstance(this).userDao()
        val userRepository = UserRepository(userDao)
        val viewModelFactory = UserViewModelFactory(userRepository)

        viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

        val editTextId = binding.etId
        val editTextPassword = binding.etPassword
        val editTextConfirmPassword = binding.etPasswordConfirm

        binding.btnSignup.setOnClickListener {
            if (editTextId.text.trim().isEmpty() || editTextPassword.text.trim()
                    .isEmpty() || editTextConfirmPassword.text.trim().isEmpty()
            ) {
                Toast.makeText(this, "Email or Password is empty", Toast.LENGTH_SHORT).show()
            } else if (editTextPassword.text.toString() != editTextConfirmPassword.text.toString()) {
                Toast.makeText(this, "Confirm password is wrong", Toast.LENGTH_SHORT).show()
            } else {
                insertUser()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Sign up success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertUser() {
        val inputEmail = binding.etId.text.toString()
        val inputPassword = binding.etPassword.text.toString()

        val newUser = User(0, inputEmail, inputPassword)
        viewModel.addUser(newUser)
    }

    private fun inputCheck(inputEmail: String, inputPassword: String): Boolean {
        return (!TextUtils.isEmpty(inputEmail) && !TextUtils.isEmpty(inputPassword))
    }
}