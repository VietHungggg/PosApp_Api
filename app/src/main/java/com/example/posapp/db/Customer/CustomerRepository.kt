package com.example.posapp.db.Customer

import androidx.lifecycle.LiveData


class CustomerRepository(private val customerDao: CustomerDao) {

    val getAllCustomer: LiveData<List<Customer>> = customerDao.getAllCustomer()

    suspend fun insertCustomer(customer: Customer) {
        customerDao.insertCustomer(customer)
    }
}