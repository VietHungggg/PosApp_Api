package com.example.posapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance2 {

//    val api:MealApi
//    init {
//    }
//    tương tự như dòng dưới đây

    val api: MealToCartApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealToCartApi::class.java)
    }
}