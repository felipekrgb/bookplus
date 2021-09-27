package com.example.book.repository

import com.example.book.model.Book
import com.example.book.service.GoogleBookAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class BooksRepository @Inject constructor(private val bookService: GoogleBookAPIService) {

    suspend fun getBooksByTerms(terms: String, startIndex: Int): List<Book>? {
        return withContext(Dispatchers.Default) {
            val bookResponse = bookService.getBooks(terms, startIndex)
            val response = processData(bookResponse)
            response?.items
        }
    }

    suspend fun getBookById(id: String): Book? {
        return withContext(Dispatchers.Default) {
            val bookResponse = bookService.getBook(id)
            processData(bookResponse)
        }
    }

    private fun <T> processData(response: Response<T>): T? {
        return if (response.isSuccessful) response.body() else null
    }
}
