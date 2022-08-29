package com.example.posapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posapp.R
import com.example.posapp.databinding.ReceiptListBinding
import com.example.posapp.db.Customer.Customer
import com.example.posapp.db.receipt.Receipt

class ReceiptAdapter : RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder>() {

    private var receiptList = ArrayList<Receipt>()

    inner class ReceiptViewHolder(val binding: ReceiptListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        return ReceiptViewHolder(
            ReceiptListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        var currentItem = receiptList[position]
        "Receipt ID : ${currentItem.id}".also { holder.binding.tvReceiptId.text = it }
        "Total Price : ${currentItem.totalPrice}".also { holder.binding.tvTotalPrice.text = it }
        "Payment date : ${currentItem.date}".also {
            holder.binding.tvDate.text = it
        }
    }

    fun setData(receipt: List<Receipt>) {
        this.receiptList = receipt as ArrayList<Receipt>
    }

    override fun getItemCount(): Int {
        return receiptList.size
    }
}