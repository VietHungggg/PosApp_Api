package com.example.posapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.posapp.R
import com.example.posapp.activities.*
import com.example.posapp.adapters.CategoriesAdapter
import com.example.posapp.adapters.MostPopularAdapter
import com.example.posapp.api.MealsByCategory
import com.example.posapp.api.Meal
import com.example.posapp.databinding.FragmentHomeBinding
import com.example.posapp.fragments.customer.AddInfoFragment
import com.example.posapp.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.example.posapp.fragments.idMeal"
        const val MEAL_NAME = "com.example.posapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.posapp.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.posapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)

        super.onCreate(savedInstanceState)

        //        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java] (homeMvvm = viewModel)
        //        ViewModel Factory
        viewModel = (activity as MainActivity).viewModel

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //        Prepare RecView
        preparePopularItemsRecyclerView()
        prepareCategoriesRecyclerView()

        //        Random item
        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        //        Popular item
        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        //        Category
        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()

        registerForContextMenu(binding.imgCustomerThreePoint)

        insertDefault()
    }

    private fun insertDefault() {
        viewModel.defaultMeal()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu?.add(1, 1, 1, "Add Info")
        menu?.add(1, 2, 2, "My Info")
        menu?.add(1, 3, 3, "Log out")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            1 -> {
                findNavController().navigate(R.id.action_homeFragment_to_addInfoFragment)
                return true
            }
            2 -> {
                Toast.makeText(activity, "My info", Toast.LENGTH_SHORT).show()
                return true
            }
            3 -> {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    //    Event click -> new View

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

    //    Click on Category
    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
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
        viewModel.observePopularItemsLiveData()
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
        viewModel.observeRandomMealLivedata().observe(
            viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData()
            .observe(viewLifecycleOwner, Observer { categories ->
                categoriesAdapter.setCategoriesList(categories)
            })
    }
}