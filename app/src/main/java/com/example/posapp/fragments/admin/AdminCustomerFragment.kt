package com.example.posapp.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.posapp.R
import com.example.posapp.databinding.FragmentAdminCustomerBinding

class AdminCustomerFragment : Fragment() {

    private lateinit var binding: FragmentAdminCustomerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminCustomerBinding.inflate(inflater)
        return binding.root
    }



}