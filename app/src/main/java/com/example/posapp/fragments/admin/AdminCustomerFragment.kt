package com.example.posapp.fragments.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posapp.adapters.CartMealsAdapter
import com.example.posapp.adapters.CustomerListAdapter
import com.example.posapp.databinding.FragmentAdminCustomerBinding
import com.example.posapp.db.Customer.CustomerRepository
import com.example.posapp.db.MealDatabase
import com.example.posapp.db.User.UserRepository
import com.example.posapp.viewModel.CustomerViewModel
import com.example.posapp.viewModel.CustomerViewModelFactory
import com.example.posapp.viewModel.UserViewModel
import com.example.posapp.viewModel.UserViewModelFactory

class AdminCustomerFragment : Fragment() {
    private lateinit var adapter: CustomerListAdapter
    private lateinit var viewModel: CustomerViewModel
    private lateinit var binding: FragmentAdminCustomerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminCustomerBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customerDao = MealDatabase.getInstance(requireContext()).customerDao()
        val customerRepository = CustomerRepository(customerDao)
        val viewModelFactory = CustomerViewModelFactory(customerRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CustomerViewModel::class.java]

        prepareRecyclerView()
        observe()
    }

    private fun prepareRecyclerView() {
        adapter = CustomerListAdapter()
        val recyclerView = binding.recCustomerList
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun observe() {
        viewModel.getAllCustomer.observe(viewLifecycleOwner, Observer {
            it.forEach {
                Log.d("TAG", it.customerName)
            }
            adapter.setData(it)
        })
    }
}

