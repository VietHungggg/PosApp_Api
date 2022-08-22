package com.example.posapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posapp.api.MealsByCategory
import com.example.posapp.databinding.CustomerListBinding
import com.example.posapp.db.Customer.Customer


class CustomerListAdapter : RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder>() {

    private var customerList = ArrayList<Customer>()

    inner class CustomerViewHolder(val binding: CustomerListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(
            CustomerListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {

        val currentItem = customerList[position]
        "Name : ${currentItem.customerName}".also { holder.binding.tvCustomerName.text = it }
        "Birth : ${currentItem.customerBirth}".also { holder.binding.tvCustomerBirth.text = it }
        "Address : ${currentItem.customerAddress}".also {
            holder.binding.tvCustomerAddress.text = it
        }
    }

    fun setData(customer: List<Customer>) {
        this.customerList = customer as ArrayList<Customer>
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return customerList.size
    }
}