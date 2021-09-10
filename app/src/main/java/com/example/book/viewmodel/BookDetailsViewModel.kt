package com.example.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book.model.Book
import com.example.book.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val repository: BooksRepository
) : ViewModel() {

    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?> = _book

    fun getBookById(id: String) {
        viewModelScope.launch {
            repository.getBookById(id)?.let {
                _book.value = it
            }
        }
    }

}