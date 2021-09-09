package com.example.book.repository

import com.example.book.model.Book
import com.example.book.model.BookResponse
import com.example.book.service.GoogleBookAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BooksRepository @Inject constructor(private val bookService: GoogleBookAPIService) {

    suspend fun getBooksByTerms(terms: String): List<Book>? {
        return withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            val bookResponse = bookService.getBooks(terms)
            val response = processData(bookResponse)
            response?.items
        }
    }

    private fun <T> processData(response: Response<T>): T? {
        return if (response.isSuccessful) response.body() else null
    }
}
