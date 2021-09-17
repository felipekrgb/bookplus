package com.example.book.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserCategoriesDAO {

    @Query("SELECT categories FROM UserCategories")
    fun getCategories(): List<String>

    @Insert
    fun insertCategories(categories: List<String>)
}