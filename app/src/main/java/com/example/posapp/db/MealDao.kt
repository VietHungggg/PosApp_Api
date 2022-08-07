package com.example.posapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.posapp.api.Meal


@Dao
interface MealDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(meal: Meal)

    @Delete
    suspend fun delete (meal: Meal)

//    lấy tất cả các phần tử dc lưu trong db ra
    @Query ("SELECT * FROM mealsInformation")
    fun getAllMeals() : LiveData<List<Meal>>
}