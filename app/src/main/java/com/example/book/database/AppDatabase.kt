package com.example.book.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.book.database.dao.UserCategoriesDAO
import com.example.book.model.UserCategories

@Database(entities = [UserCategories::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserCategoriesDAO(): UserCategoriesDAO

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "book_plus_db"
            ).fallbackToDestructiveMigration().build()
        }
    }

}