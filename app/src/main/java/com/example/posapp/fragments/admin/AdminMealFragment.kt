package com.example.posapp.fragments.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.posapp.R
import com.example.posapp.activities.AdminRepositoryActivity
import com.example.posapp.adapters.AdminCategoriesAdapter
import com.example.posapp.adapters.CategoriesAdapter
import com.example.posapp.databinding.ActivityMealBinding
import com.example.posapp.databinding.FragmentAdminMealBinding
import com.example.posapp.db.MealDatabase
import com.example.posapp.viewModel.AdminMealViewModel
import com.example.posapp.viewModel.AdminMealViewModelFactory
import com.example.posapp.viewModel.MealViewModel
import com.example.posapp.viewModel.MealViewModelFactory

class AdminMealFragment : Fragment() {

    private lateinit var binding: FragmentAdminMealBinding
    private lateinit var viewModel: AdminMealViewModel
    private lateinit var categoriesAdapter: AdminCategoriesAdapter

    companion object {
        const val AD_CATEGORY_NAME = "com.example.posapp.fragments.admin.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mealDatabase = MealDatabase.getInstance(requireContext())
        val viewModelFactory = AdminMealViewModelFactory(mealDatabase)
        viewModel = ViewModelProvider(this, viewModelFactory)[AdminMealViewModel::class.java]

        categoriesAdapter = AdminCategoriesAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminMealBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = {
            val intent = Intent(activity, AdminRepositoryActivity::class.java)
            intent.putExtra(AD_CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        binding.recCategoriesList.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategoriesList(categories)
        }
    }
}