package com.example.book.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.book.database.dao.UserCategoriesDAO
import com.example.book.model.UserCategories

@Database(entities = [UserCategories::class], version = 2)
@TypeConverters(Converter::class)
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