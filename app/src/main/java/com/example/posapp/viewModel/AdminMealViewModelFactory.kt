package com.example.posapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.posapp.db.MealDatabase


class AdminMealViewModelFactory(private val mealDatabase: MealDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AdminMealViewModel(mealDatabase) as T
    }
}