package com.example.book.service

import com.example.book.model.BookResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBookAPIService {

    @GET("/books/v1/volumes")
    fun getBook(
        @Query("q") searchTerms: String,
        @Query("key") key: String = "AIzaSyDGk_-8FF3wUUiNyU8tRiq7AKkN_uayDcE"
    ): Call<BookResponse>

}