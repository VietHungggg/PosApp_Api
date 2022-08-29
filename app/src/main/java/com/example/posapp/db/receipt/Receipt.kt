package com.example.posapp.db.receipt

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "paymentInformation")
class Receipt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val totalPrice: String,
    val date: String,
)