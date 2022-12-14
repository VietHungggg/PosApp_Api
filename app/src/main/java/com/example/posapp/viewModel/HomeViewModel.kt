package com.example.posapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posapp.api.*
import com.example.posapp.db.Customer.Customer
import com.example.posapp.db.MealDatabase
import com.example.posapp.db.ReceiptDetails.ReceiptDetails
import com.example.posapp.db.receipt.Receipt
import com.example.posapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.channels.MulticastChannel


class HomeViewModel(private val mealDatabase: MealDatabase) : ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData = mealDatabase.mealDao().getAllMeals()
    private var cartMealsLiveData = mealDatabase.mealToCartDao().getAllMealsCart()
    private var receiptLiveData = mealDatabase.receiptDao().getAllReceipt()
    var sum = MutableLiveData<Int>()
    var maxId = MutableLiveData<Int>()
    var newCustomer = MutableLiveData<Int>()
    var income = MutableLiveData<Int>()
    init {
        sum.value = 0
    }

    //    Random meal image
    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    //    Popular image
    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        popularItemsLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("HomeFragment", t.message.toString())
                }
            })
    }

    //    Category image
    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    categoriesLiveData.value = response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString())
            }
        })
    }

    // Delete meal from Favorite

    fun defaultMeal() {
        viewModelScope.launch {
            mealDatabase.mealToCartDao()
                .updateMealCart(MealToCart("0", "0", "0", "0", "0", "0", "0", "0"))
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun deleteMealCart(mealToCart: MealToCart) {
        if (mealToCart.idMeal != "0") {
            viewModelScope.launch {
                mealDatabase.mealToCartDao().deleteMealCart(mealToCart)
            }
        }
    }

    fun deleteCartReset() {
        viewModelScope.launch {
            mealDatabase.mealToCartDao().deleteResetCart()
        }
    }

    fun idMax() {
        viewModelScope.launch {
            maxId.value = mealDatabase.receiptDao().idMax()
        }
    }

    fun customerNew(){
        viewModelScope.launch {
            newCustomer.value = mealDatabase.customerDao().customerNew()
        }
    }

    fun income(){
        viewModelScope.launch{
            income.value = mealDatabase.receiptDao().income()
        }
    }

    fun sumPrice() {
        viewModelScope.launch {
            sum.value = mealDatabase.mealToCartDao().sumPrice()
        }
    }

    fun insertReceipt(receipt: Receipt) {
        viewModelScope.launch {
            mealDatabase.receiptDao().insertReceipt(receipt)
        }
    }

    fun insertReceiptDetails(receiptDetails: ReceiptDetails) {
        viewModelScope.launch {
            mealDatabase.receiptDetailsDao().insertReceiptDetails(receiptDetails)
        }
    }

//    fun deleteCart(){
//        viewModelScope.launch {
//            mealDatabase.mealToCartDao().deleteResetCart()
//        }
//    }

    fun observerReceiptLiveData(): LiveData<List<Receipt>> {
        return receiptLiveData
    }

    fun observeRandomMealLivedata(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observeFavoritesMealsLiveData(): LiveData<List<Meal>> {
        return favoritesMealsLiveData
    }

    fun observeCartMealsLiveData(): LiveData<List<MealToCart>> {
        return cartMealsLiveData
    }


}