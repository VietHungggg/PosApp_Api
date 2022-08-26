package com.example.posapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posapp.api.*
import com.example.posapp.db.MealDatabase
import com.example.posapp.retrofit.RetrofitInstance
import com.example.posapp.retrofit.RetrofitInstance2
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MealViewModel(val mealDatabase: MealDatabase) : ViewModel() {

    private val mealDetailsLiveData = MutableLiveData<Meal>()
    private val favoritesLiveData = MutableLiveData<List<Meal>>()
    private val cartLiveData = MutableLiveData<List<MealToCart>>()

    fun getMealDetail(id: String) {
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                } else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity", t.message.toString())
            }
        })
    }

    fun observerMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().update(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    //    Meal to Cart
    fun insertMealToCart(mealToCart: MealToCart) {
        viewModelScope.launch {
            mealDatabase.mealToCartDao().updateMealCart(mealToCart)
        }
    }

    fun deleteMealToCart(mealToCart: MealToCart) {
        viewModelScope.launch {
            mealDatabase.mealToCartDao().deleteMealCart(mealToCart)
        }
    }

    fun observeFavoritesLiveData(): LiveData<List<Meal>> {
        return favoritesLiveData
    }

    fun observeCartLiveData(): LiveData<List<MealToCart>> {
        return cartLiveData
    }
}