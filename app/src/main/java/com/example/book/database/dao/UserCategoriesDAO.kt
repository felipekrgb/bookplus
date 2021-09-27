package com.example.book.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.book.model.UserCategories

@Dao
interface UserCategoriesDAO {

    @Query("SELECT * FROM UserCategories WHERE user_id = :userId")
    suspend fun getUserCategories(userId: String): UserCategories?

    @Insert
    suspend fun insertUserCategories(userCategories: UserCategories)

    @Update
    suspend fun updateUserCategories(userCategories: UserCategories)
}