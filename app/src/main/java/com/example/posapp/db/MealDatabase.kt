package com.example.posapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.posapp.api.Meal
import com.example.posapp.api.MealToCart
import com.example.posapp.db.Customer.Customer
import com.example.posapp.db.Customer.CustomerDao
import com.example.posapp.db.ReceiptDetails.ReceiptDetails
import com.example.posapp.db.ReceiptDetails.ReceiptDetailsDao
import com.example.posapp.db.User.User
import com.example.posapp.db.User.UserDao
import com.example.posapp.db.receipt.Receipt
import com.example.posapp.db.receipt.ReceiptDao

@Database(
    entities = [Meal::class, MealToCart::class, User::class, Customer::class, Receipt::class,
        ReceiptDetails::class],
    version = 25,
    exportSchema = false
)
@TypeConverters(MealTypeConverter::class)

abstract class MealDatabase() : RoomDatabase() {

    abstract fun mealDao(): MealDao
    abstract fun mealToCartDao(): MealToCartDao
    abstract fun userDao(): UserDao
    abstract fun customerDao(): CustomerDao
    abstract fun receiptDao(): ReceiptDao
    abstract fun receiptDetailsDao(): ReceiptDetailsDao

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