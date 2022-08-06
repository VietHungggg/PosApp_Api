package com.example.posapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.posapp.activities.MealActivity
import com.example.posapp.adapters.CategoriesAdapter
import com.example.posapp.adapters.MostPopularAdapter
import com.example.posapp.api.MealsByCategory
import com.example.posapp.api.Meal
import com.example.posapp.databinding.FragmentHomeBinding
import com.example.posapp.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.example.posapp.fragments.idMeal"
        const val MEAL_NAME = "com.example.posapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.posapp.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
        popularItemsAdapter = MostPopularAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    //    Event click -> new View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //        Prepare RecView
        preparePopularItemsRecyclerView()
        prepareCategoriesRecyclerView()

        //        Random item
        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        //        Popular item
        homeMvvm.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        //        Category
        homeMvvm.getCategories()
        observeCategoriesLiveData()

    }

    //    Click into PopularItem
    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    //    Click into RandomMeal
    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    //    Prepare RecView of Popular
    //    GridLayout : 3 la so phan tu dc chia theo hang ngang
    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    //    Prepare RecView of Category
    private fun prepareCategoriesRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    //    Observe

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData()
            //            .observe(viewLifecycleOwner, object : Observer<List<CategoryMeals>> {
            //                override fun onChanged(t: List<CategoryMeals>?) {
            //
            //                }
            //            })  - >  cách gọi hàm bình thường, bên dưới là cách gob dùng lambda

            .observe(
                viewLifecycleOwner
            ) { mealList ->
                popularItemsAdapter.setMeals(mealList = mealList as ArrayList<MealsByCategory>)
            }

    }


    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLivedata().observe(
            viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }

    private fun observeCategoriesLiveData() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoriesList(categories)
        })
    }
}