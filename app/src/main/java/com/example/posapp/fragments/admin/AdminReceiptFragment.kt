package com.example.posapp.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posapp.R
import com.example.posapp.activities.MainActivity
import com.example.posapp.adapters.ReceiptAdapter
import com.example.posapp.databinding.FragmentAdminReceiptBinding
import com.example.posapp.db.MealDatabase
import com.example.posapp.viewModel.HomeViewModel
import com.example.posapp.viewModel.HomeViewModelFactory
import com.example.posapp.viewModel.MealViewModel
import com.example.posapp.viewModel.MealViewModelFactory

class AdminReceiptFragment : Fragment() {

    private lateinit var adapter: ReceiptAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentAdminReceiptBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminReceiptBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealDatabase = MealDatabase.getInstance(requireContext())
        val viewModelFactory = HomeViewModelFactory(mealDatabase)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        prepareRecyclerView()
        observer()
    }

    private fun observer() {
        viewModel.observerReceiptLiveData().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun prepareRecyclerView() {
        adapter = ReceiptAdapter()
        val recyclerView = binding.recReceipts
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


}