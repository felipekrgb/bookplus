package com.example.book.repository

import com.example.book.model.Book
import com.example.book.model.BookResponse
import com.example.book.service.GoogleBookAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BooksRepository @Inject constructor(private val bookService: GoogleBookAPIService) {

    fun getBooksByTerms(terms: String, onComplete: (List<Book>?, String?) -> Unit) {

        val call = bookService.getBook(terms)
        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(
                call: Call<BookResponse>,
                response: Response<BookResponse>
            ) {
                println(response.body())
                if (response.body() != null) {
                    onComplete(response.body()!!.items, null)
                } else {
                    onComplete(null, "Ocorreu um erro com a API")
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                onComplete(null, "Ocorreu um erro de conexão com a API")
            }

        })
    }
}
