package com.example.book.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserCategories(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "favorite_categories_id")
    val id: Long = 0,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "categories")
    val categories: List<String>
)