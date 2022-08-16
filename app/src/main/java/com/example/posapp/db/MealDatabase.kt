package com.example.posapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.posapp.api.Meal
import com.example.posapp.api.MealToCart
import com.example.posapp.db.User.User
import com.example.posapp.db.User.UserDao


@Database(
    entities = [Meal::class, MealToCart::class, User::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(MealTypeConverter::class)

abstract class MealDatabase() : RoomDatabase() {

    abstract fun mealDao(): MealDao
    abstract fun mealToCartDao(): MealToCartDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        var INSTANCE: MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MealDatabase
        }
    }
}