package com.example.posapp.api

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mealPrice")
class MealPrice(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val strCategory: String,
    val price: String
)