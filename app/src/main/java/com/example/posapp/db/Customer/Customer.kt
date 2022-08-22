package com.example.posapp.db.Customer

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "customerInformation")
class Customer(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val customerName: String,
    val customerBirth: String,
    val customerAddress: String
)