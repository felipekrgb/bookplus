package com.example.book.viewmodel

import androidx.lifecycle.ViewModel
import com.example.book.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasicDetailsViewModel @Inject constructor(

    private val repository: BooksRepository

) : ViewModel() {
    // TODO: Implement the ViewModel
}