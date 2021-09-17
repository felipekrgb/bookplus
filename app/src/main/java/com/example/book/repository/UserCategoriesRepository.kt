package com.example.book.repository

import com.example.book.database.dao.UserCategoriesDAO
import com.example.book.model.UserCategories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserCategoriesRepository @Inject constructor(
    private val userCategoriesDAO: UserCategoriesDAO
) {

    suspend fun getUserCategories(userId: String): UserCategories? {
        return withContext(Dispatchers.Default) {
            userCategoriesDAO.getUserCategories(userId)
        }
    }

    suspend fun addUserCategories(userCategories: UserCategories) {
        return withContext(Dispatchers.Default) {
            userCategoriesDAO.insertUserCategories(userCategories)
        }
    }

}