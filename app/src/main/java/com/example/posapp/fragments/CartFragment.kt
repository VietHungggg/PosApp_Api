package com.example.posapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

                Snackbar.make(requireView(), "Meal remove from Cart!!", Snackbar.LENGTH_SHORT)
                    .show()
//                    .setAction("Undo", View.OnClickListener {
//                        viewModel.insertMeal(cartAdapter.differ.currentList[position])
//                    }).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recCart)

        prepareRecyclerView()
        observeCart()
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