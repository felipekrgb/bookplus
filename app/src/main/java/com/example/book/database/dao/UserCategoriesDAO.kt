package com.example.book.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.book.model.UserCategories

@Dao
interface UserCategoriesDAO {

    @Query("SELECT * FROM UserCategories WHERE user_id = :userId")
    fun getUserCategories(userId: String): UserCategories

    @Insert
    fun insertUserCategories(userCategories: UserCategories)
}