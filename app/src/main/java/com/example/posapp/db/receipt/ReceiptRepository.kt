package com.example.posapp.db.receipt

import androidx.lifecycle.LiveData

class ReceiptRepository(private val receiptDao: ReceiptDao) {

    val getAllReceipt: LiveData<List<Receipt>> = receiptDao.getAllReceipt()

    suspend fun insertReceipt(receipt: Receipt) {
        receiptDao.insertReceipt(receipt)
    }
}