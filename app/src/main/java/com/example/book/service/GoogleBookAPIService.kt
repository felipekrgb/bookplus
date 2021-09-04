package com.example.book.service

import com.example.book.model.BookResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBookAPIService {

    @GET("/volumes")
    fun getBook(@Query("q") searchTerms: String): Call<BookResponse>

}