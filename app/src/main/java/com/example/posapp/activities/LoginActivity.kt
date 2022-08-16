package com.example.posapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.posapp.databinding.ActivityLoginBinding
import com.example.posapp.db.MealDatabase
import com.example.posapp.db.User.UserRepository
import com.example.posapp.viewModel.UserViewModel
import com.example.posapp.viewModel.UserViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDao = MealDatabase.getInstance(this).userDao()
        val userRepository = UserRepository(userDao)
        val viewModelFactory = UserViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.inputEmpty.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.getUserLogin.observe(this) {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        viewModel.inputError.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        binding.tvSingup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}