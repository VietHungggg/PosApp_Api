package com.example.posapp.db.User

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userInformation")
class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userEmail: String?,
    val userPassword: String?
)