package com.example.posapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.posapp.R
import com.example.posapp.adapters.AdminCategoriesAdapter
import com.example.posapp.adapters.CategoryMealsAdapter
import com.example.posapp.adapters.CategoryMealsAdapter2
import com.example.posapp.databinding.ActivityAdminRepositoryBinding
import com.example.posapp.databinding.CategoryItemsBinding
import com.example.posapp.fragments.admin.AdminMealFragment
import com.example.posapp.viewModel.AdminMealViewModel
import com.example.posapp.viewModel.CategoryMealsViewModel

class AdminRepositoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdminRepositoryBinding
    lateinit var viewModel: CategoryMealsViewModel
    lateinit var categoriesAdapter: CategoryMealsAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()
        viewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        viewModel.getMealsByCategory(intent.getStringExtra(AdminMealFragment.AD_CATEGORY_NAME)!!)

        viewModel.observeMealsLiveData().observe(this) {
            binding.tvCategoryCount.text = "Quantify : ${it.size.toString()}"
            categoriesAdapter.setMealsList(it)
        }
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoryMealsAdapter2()
        binding.recMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }
}