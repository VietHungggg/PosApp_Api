package com.example.posapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posapp.api.Meal
import com.example.posapp.api.MealToCart
import com.example.posapp.databinding.CartItemBinding
import kotlin.math.cos


class CartMealsAdapter() : RecyclerView.Adapter<CartMealsAdapter.CartMealsAdapterViewHolder>() {

    inner class CartMealsAdapterViewHolder(val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<MealToCart>() {
        override fun areItemsTheSame(oldItem: MealToCart, newItem: MealToCart): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealToCart, newItem: MealToCart): Boolean {
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

    override fun onBindViewHolder(holder: CartMealsAdapterViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgCartItem)
        holder.binding.tvCartItemName.text = meal.strMeal
        holder.binding.tvCartItemCost.text = "Price :"
        holder.binding.tvCartItemCost2.text = "${meal.price} ¥"

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}