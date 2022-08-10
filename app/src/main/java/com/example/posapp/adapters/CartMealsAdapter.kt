package com.example.posapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posapp.api.Meal
import com.example.posapp.databinding.CartItemBinding
import kotlin.math.cos


class CartMealsAdapter() : RecyclerView.Adapter<CartMealsAdapter.CartMealsAdapterViewHolder>() {

    inner class CartMealsAdapterViewHolder(val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartMealsAdapterViewHolder {
        return CartMealsAdapterViewHolder(
            CartItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    val cost = listOf<Int>(800, 850, 900, 950, 1000, 1100, 1200)

    override fun onBindViewHolder(holder: CartMealsAdapterViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgCartItem)
        holder.binding.tvCartItemName.text = meal.strMeal
        holder.binding.tvCartItemCost.text = "Cost :"
        holder.binding.tvCartItemCost2.text = "${cost.random()} ¥"

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}