package com.example.posapp.db.ReceiptDetails

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "receiptDetail")
class ReceiptDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idForeignKey: Int,
    val mealName: String,
    val mealPrice: String,
    val totalPrice: String,
    val date: String,
)