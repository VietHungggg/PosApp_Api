package com.example.posapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.posapp.api.MealPrice
import com.example.posapp.api.MealToCart


@Dao
interface MealPriceDao {

    @Query("SELECT * FROM mealPrice")
    fun getAllPrice(): LiveData<List<MealPrice>>
}