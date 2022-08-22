package com.example.posapp.fragments.customer

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.posapp.R
import com.example.posapp.databinding.FragmentAddInfoBinding
import com.example.posapp.db.Customer.Customer
import com.example.posapp.db.Customer.CustomerRepository
import com.example.posapp.db.MealDatabase
import com.example.posapp.viewModel.CustomerViewModel
import com.example.posapp.viewModel.CustomerViewModelFactory

class AddInfoFragment : Fragment() {

    private lateinit var binding: FragmentAddInfoBinding
    private lateinit var customerViewModel: CustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val customerDao = MealDatabase.getInstance(requireContext()).customerDao()
        val customerRepository = CustomerRepository(customerDao)
        val viewModelFactory = CustomerViewModelFactory(customerRepository)
        customerViewModel = ViewModelProvider(this, viewModelFactory)[CustomerViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddInfoBinding.inflate(inflater, container, false)
//        customerViewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)

        val customerName = binding.etCustomerName
        val customerBirth = binding.etCustomerBirth
        val customerAddress = binding.etCustomerAddress

        binding.btnInsertInfo.setOnClickListener {
            if (customerName.text.toString().trim().isEmpty() || customerBirth.text.toString()
                    .trim()
                    .isEmpty() || customerAddress.text.toString().trim().isEmpty()
            ) {
                Toast.makeText(activity, "Information is empty", Toast.LENGTH_SHORT).show()
            } else {
                insertCustomer()
            }
        }
        return binding.root
    }

    private fun insertCustomer() {
        val inputCustomerName = binding.etCustomerName.text.toString()
        val inputCustomerBirth = binding.etCustomerBirth.text.toString()
        val inputCustomerAddress = binding.etCustomerAddress.text.toString()

        if (inputCheck(inputCustomerName, inputCustomerBirth, inputCustomerAddress)) {

            val newCustomer =
                Customer(0, inputCustomerName, inputCustomerBirth, inputCustomerAddress)
            customerViewModel.insertCustomer(newCustomer)

            findNavController().navigate(R.id.action_addInfoFragment_to_homeFragment)
            Toast.makeText(requireContext(), "Insert information success", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(
        inputCustomerName: String,
        inputCustomerBirth: String,
        inputCustomerAddress: String
    ): Boolean {
        return (!TextUtils.isEmpty(inputCustomerName) && !TextUtils.isEmpty(inputCustomerBirth) && !TextUtils.isEmpty(
            inputCustomerAddress
        ))
    }

}