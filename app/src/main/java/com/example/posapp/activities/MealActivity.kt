package com.example.posapp.activities

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.posapp.R
import com.example.posapp.api.Meal
import com.example.posapp.api.MealToCart
import com.example.posapp.databinding.ActivityMealBinding
import com.example.posapp.db.MealDatabase
import com.example.posapp.fragments.HomeFragment
import com.example.posapp.viewModel.MealViewModel
import com.example.posapp.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Meal viewmodel Factory using RoomDB
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        //Event function
        getMealInformationFromIntent()
        setInformationInView()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
//        mealMvvm.getMealToCartDetail(mealId)

        observerMealDetailsLiveData()

        //Click function
        onYoutubeImageClick()
        onCartClick()
        onFavoriteClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun onCartClick() {
        binding.btnChoiceToCart.setOnClickListener {
            mealToSave?.let {
                var price: Int
                if (it.strCategory?.isEmpty() == true) {
                    price = 0
                } else {
                    price = when (it.strCategory) {
                        "Beef" -> 1100
                        "Chicken" -> 800
                        "Dessert" -> 700
                        "Lamb" -> 1200
                        "Miscellaneous" -> 750
                        "Pasta" -> 850
                        "Pork" -> 900
                        "Seafood" -> 950
                        "Side" -> 650
                        "Starter" -> 600
                        "Vegan" -> 700
                        "Vegetarian" -> 1000
                        "Breakfast" -> 700
                        else -> 800
                    }
                    mealMvvm.insertMealToCart(
                        MealToCart(
                            it.dateModified,
                            it.idMeal,
                            it.strArea,
                            it.strCategory,
                            it.strMeal,
                            it.strMealThumb,
                            it.strYoutube,
                            price.toString()
                        )
                    )
                    Toast.makeText(this, "Meal save to Cart!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal save to Favorites!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //    Create var  click to btn favorite and cart
    private var mealToSave: Meal? = null

    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {

                onResponseCase()

                val meal = t
                mealToSave = meal
                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal!!.strArea}"
                binding.tvInstructionSteep.text = meal.strInstructions
                youtubeLink = meal.strYoutube.toString()
            }
        })
    }

    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.oceanblue))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.btnChoiceToCart.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.btnChoiceToCart.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}