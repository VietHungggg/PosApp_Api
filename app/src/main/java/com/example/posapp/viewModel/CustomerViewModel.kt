package com.example.posapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posapp.db.Customer.Customer
import com.example.posapp.db.Customer.CustomerDao
import com.example.posapp.db.Customer.CustomerRepository
import com.example.posapp.db.MealDatabase
import kotlinx.coroutines.launch


class CustomerViewModel(private val repository: CustomerRepository) : ViewModel() {

    var getAllCustomer: LiveData<List<Customer>> =
        repository.getAllCustomer

    fun insertCustomer(customer: Customer) {
        viewModelScope.launch {
            repository.insertCustomer(customer)
        }
    }
}