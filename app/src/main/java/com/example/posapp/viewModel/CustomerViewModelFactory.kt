package com.example.posapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.posapp.db.Customer.CustomerRepository
import com.example.posapp.db.MealDatabase
import com.example.posapp.db.User.UserRepository


class CustomerViewModelFactory(private val repository: CustomerRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomerViewModel(repository) as T
    }
}