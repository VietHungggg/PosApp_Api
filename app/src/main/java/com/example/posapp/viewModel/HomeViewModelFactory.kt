package com.example.posapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.posapp.db.MealDatabase


class HomeViewModelFactory(private val mealDatabase: MealDatabase) : ViewModelProvider.Factory {

//    Return MealViewModel lay data tu DB
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }
}