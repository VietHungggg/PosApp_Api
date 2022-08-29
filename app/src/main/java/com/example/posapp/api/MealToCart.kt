package com.example.posapp.api

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mealToCart")

data class MealToCart(
    val dateModified: Any?,

    @PrimaryKey
    val idMeal: String,
    val strArea: String?,
    val strCategory: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strYoutube: String?,
    val price: String?,
    val status: Int = 0,
    val idOder: Int = 0,
)