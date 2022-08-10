package com.example.posapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.posapp.api.Meal
import com.example.posapp.api.MealToCart


@Dao
interface MealToCartDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(mealToCart: MealToCart)

    @Delete
    suspend fun delete (mealToCart: MealToCart)

//    Save data to favorites
    @Query ("SELECT * FROM mealsInformation")
    fun getAllMeals() : LiveData<List<MealToCart>>

}