package com.example.book.di

import com.example.book.repository.BooksRepository
import com.example.book.service.GoogleBookAPIService
import com.example.book.service.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideBookService(): GoogleBookAPIService = RetrofitBuilder.getGoogleBookAPIService()

    @Provides
    fun provideBookRepository(service: GoogleBookAPIService): BooksRepository =
        BooksRepository(service)
}