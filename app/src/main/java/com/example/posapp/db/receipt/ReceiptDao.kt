package com.example.posapp.db.receipt

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ReceiptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receipt: Receipt)

    @Query("SELECT * FROM paymentInformation ORDER BY id ASC")
    fun getAllReceipt(): LiveData<List<Receipt>>

    @Query("SELECT MAX(id) FROM paymentInformation")
    suspend fun idMax() : Int

    @Query("SELECT SUM(totalPrice) FROM paymentInformation")
    suspend fun income() : Int
}