package com.example.posapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posapp.api.Category
import com.example.posapp.api.Meal
import com.example.posapp.api.MealsByCategory
import com.example.posapp.databinding.MealsItemBinding


class CategoryMealsAdapter2 : RecyclerView.Adapter<CategoryMealsAdapter2.CategoryMeals2ViewModel>() {

    private var mealsList = ArrayList<MealsByCategory>()

    fun setMealsList(mealsList: List<MealsByCategory>) {
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class CategoryMeals2ViewModel(val binding: MealsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMeals2ViewModel {
        return CategoryMeals2ViewModel(
            MealsItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryMeals2ViewModel, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealsList[position].strMeal
        holder.itemView.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

}