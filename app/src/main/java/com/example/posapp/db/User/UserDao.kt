package com.example.posapp.db.User

import androidx.room.*


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User?)

    @Delete
    suspend fun deleteUser(user: User?)

    @Query("SELECT * FROM userInformation WHERE userEmail = :userEmail AND userPassword = :userPassword")
    suspend fun userLogin(userEmail: String, userPassword: String): User?
}