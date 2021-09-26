package com.example.book

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.book.database.AppDatabase
import com.example.book.database.dao.UserCategoriesDAO
import com.example.book.model.UserCategories
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserCategoriesDAOTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: UserCategoriesDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).fallbackToDestructiveMigration().build()
        dao = database.getUserCategoriesDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insert_user_categories_returns_true() = runBlocking {
        val categories = listOf("Ação", "Aventura", "Terror")
        val userCategories = UserCategories(1L, "123ABC", categories)

        dao.insertUserCategories(userCategories)

        val results = dao.getUserCategories("123ABC")
        assertThat(results?.categories).isEqualTo(categories)
    }
}