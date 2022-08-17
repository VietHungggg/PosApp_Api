package com.example.posapp.db.User

import com.example.posapp.db.MealDatabase


class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun userLogin(userEmail: String, userPassword: String): User? {
        return userDao.userLogin(userEmail, userPassword)
    }

    suspend fun adminLogin(userEmail: String, userPassword: String): User? {
        return userDao.adminLogin(userEmail, userPassword)
    }
}