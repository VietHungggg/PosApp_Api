package com.example.posapp.retrofit

import com.example.posapp.api.CategoryList
import com.example.posapp.api.MealsByCategoryList
import com.example.posapp.api.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MealToCartApi {
    @GET ("random.php")
    fun getRandomMealToCart() : Call<MealList>

    @GET ("lookup.php?")
    fun getMealToCartDetails (@Query("i") id : String) : Call<MealList>

    @GET ("filter.php?")
    fun getPopularItems (@Query("c") category: String) : Call<MealsByCategoryList>

    @GET ("categories.php")
    fun getCategories () : Call<CategoryList>

    @GET ("filter.php")
    fun getMealsByCategory (@Query("c") categoryName: String) : Call<MealsByCategoryList>
}