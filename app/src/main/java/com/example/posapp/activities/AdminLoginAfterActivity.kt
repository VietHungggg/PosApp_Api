package com.example.posapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.posapp.R
import com.example.posapp.db.MealDatabase
import com.example.posapp.viewModel.HomeViewModel
import com.example.posapp.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminLoginAfterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login_after)
    }
}