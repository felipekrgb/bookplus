package com.example.book.di

import android.content.Context
import com.example.book.database.AppDatabase
import com.example.book.database.dao.UserCategoriesDAO
import com.example.book.repository.AuthenticationRepository
import com.example.book.repository.BooksRepository
import com.example.book.repository.UserCategoriesRepository
import com.example.book.service.GoogleBookAPIService
import com.example.book.service.RetrofitBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideBookService(): GoogleBookAPIService = RetrofitBuilder.getGoogleBookAPIService()

    @Provides
    fun provideBookRepository(service: GoogleBookAPIService): BooksRepository =
        BooksRepository(service)

    @Provides
    fun provideRepositoryAuth(
        auth: FirebaseAuth,
        fireStore: FirebaseFirestore
    ): AuthenticationRepository {
        return AuthenticationRepository(auth, fireStore)
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    fun provideUserCategoriesDAO(@ApplicationContext context: Context): UserCategoriesDAO =
        AppDatabase.getDatabase(context).getUserCategoriesDAO()

    @Provides
    fun provideUserCategorieRepository(dao: UserCategoriesDAO): UserCategoriesRepository =
        UserCategoriesRepository(dao)
}