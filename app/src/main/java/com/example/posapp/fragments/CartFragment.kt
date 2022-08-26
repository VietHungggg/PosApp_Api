package com.example.posapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.posapp.activities.MainActivity
import com.example.posapp.adapters.CartMealsAdapter
import com.example.posapp.databinding.FragmentCartBinding
import com.example.posapp.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var cartAdapter: CartMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMealCart(cartAdapter.differ.currentList[position])
                Toast.makeText(requireContext(), "Meal remove from Cart!!", Toast.LENGTH_SHORT)
                    .show()
                sumPrice()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recCart)

        prepareRecyclerView()
        observeCart()
        sumPrice()

        viewModel.observerMealPrice().observe(viewLifecycleOwner) {
            it.forEach {
                viewModel.updatePrice(it.price, it.strCategory)
            }
        }
    }

    private fun sumPrice() {
        viewModel.sumPrice()
        viewModel.sum.observe(requireActivity()) {
            binding.tvMealPrice2.text = "$it ¥"
            val tax = (it * 0.1).toInt()
            val totalPrice = (it + it * 0.1).toInt()

            binding.tvTax2.text = "${tax.toString()} ¥"
            binding.tvTotalPrice2.text = "${totalPrice.toString()} ¥"
        }
    }

    private fun prepareRecyclerView() {
        cartAdapter = CartMealsAdapter()
        binding.recCart.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }
    }

    private fun observeCart() {
        viewModel.observeCartMealsLiveData().observe(requireActivity()) {
            cartAdapter.differ.submitList(it)
        }
    }
}