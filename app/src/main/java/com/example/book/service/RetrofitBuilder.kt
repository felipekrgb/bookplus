package com.example.book.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    // .client(
    //        OkHttpClient.Builder().addInterceptor { chain ->
    //            val url = chain.request().url().newBuilder()
    //                .addQueryParameter("key", "AIzaSyDGk_-8FF3wUUiNyU8tRiq7AKkN_uayDcE").build()
    //            chain.proceed(chain.request().newBuilder().url(url).build())
    //        }.build()
    //    )

    private val retrofit = Retrofit.Builder().baseUrl("https://www.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    fun getGoogleBookAPIService(): GoogleBookAPIService {
        return retrofit.create(GoogleBookAPIService::class.java)
    }
}