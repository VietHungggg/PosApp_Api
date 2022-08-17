package com.example.posapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.posapp.api.Meal
import com.example.posapp.api.MealToCart


@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    //    Save data to favorites
    @Query("SELECT * FROM mealsInformation")
    fun getAllMeals(): LiveData<List<Meal>>

}