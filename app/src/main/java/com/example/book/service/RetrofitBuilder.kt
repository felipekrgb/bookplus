package com.example.book.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofit = Retrofit.Builder().baseUrl("https://www.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    fun getGoogleBookAPIService(): GoogleBookAPIService {
        return retrofit.create(GoogleBookAPIService::class.java)
    }
}