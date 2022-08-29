package com.example.posapp.db.ReceiptDetails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ReceiptDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceiptDetails(receiptDetails: ReceiptDetails)

}