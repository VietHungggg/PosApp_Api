package com.example.posapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.posapp.api.MealToCart


@Dao
interface MealToCartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMealCart(mealToCart: MealToCart)

    @Delete
    suspend fun deleteMealCart(mealToCart: MealToCart)

    @Query("SELECT * FROM mealToCart")
    fun getAllMealsCart(): LiveData<List<MealToCart>>

    @Query("UPDATE mealToCart SET price = :price WHERE strCategory = :strCategory ")
    suspend fun updatePrice(price: String, strCategory: String)

    @Query("SELECT SUM(price) FROM mealToCart")
    suspend fun sumPrice() : Int
}