package com.example.book.repository

import com.example.book.database.dao.UserCategoriesDAO
import com.example.book.model.UserCategories
import javax.inject.Inject

class UserCategoriesRepository @Inject constructor(
    private val userCategoriesDAO: UserCategoriesDAO
) {

    fun getUserCategories(userId: String): UserCategories {
        return userCategoriesDAO.getUserCategories(userId)
    }

    fun addUserCategories(userCategories: UserCategories) {
        userCategoriesDAO.insertUserCategories(userCategories)
    }

}