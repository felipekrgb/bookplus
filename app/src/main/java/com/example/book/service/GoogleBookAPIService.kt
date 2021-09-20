package com.example.book.service

import com.example.book.BuildConfig
import com.example.book.model.Book
import com.example.book.model.BookResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBookAPIService {

    @GET("/books/v1/volumes")
    suspend fun getBooks(
        @Query("q") searchTerms: String,
        @Query("startIndex") startIndex: Int,
        @Query("key") key: String = BuildConfig.API_KEY,
    ): Response<BookResponse>

    @GET("/books/v1/volumes/{id}")
    suspend fun getBook(@Path("id") id: String): Response<Book>
}