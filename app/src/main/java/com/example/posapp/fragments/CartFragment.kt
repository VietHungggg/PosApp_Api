package com.example.posapp.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.posapp.R
import com.example.posapp.activities.MainActivity
import com.example.posapp.activities.PaymentDoneActivity
import com.example.posapp.adapters.CartMealsAdapter
import com.example.posapp.api.MealToCart
import com.example.posapp.api.MealToCartList
import com.example.posapp.databinding.FragmentCartBinding
import com.example.posapp.db.receipt.Receipt
import com.example.posapp.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.currentCoroutineContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = LocalDate.now()
        binding.tvDate.text = date.toString()

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
        buyClick()
//        idMax()
    }

    private fun buyClick() {

        binding.btnBuy.setOnClickListener {
            insertReceipt()
            val intent = Intent(requireContext(), PaymentDoneActivity::class.java)
            startActivity(intent)
            viewModel.deleteCartReset()
        }
    }

    private fun insertReceipt() {
        val inputMealPrice = binding.tvTotalPrice2.text.toString()
        val inputPaymentDate = binding.tvDate.text.toString()
        val newReceipt = Receipt(0, inputMealPrice, inputPaymentDate)

        viewModel.insertReceipt(newReceipt)
    }
//
//    private fun insertReceiptDetails() {
//        val inputIdForeignKey = viewModel.idMax().toString()
////        val inputMealName =
//    }

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

//    private fun idMax() {
//        viewModel.idMax()
//        viewModel.maxId.observe(requireActivity()){
//            binding.tvTest.text = it.toString()
//        }
//    }


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
            if (it.size == 1) {
                binding.btnBuy.isVisible = false
            }
        }
    }
}