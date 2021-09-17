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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getBookById(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getBookById(id)?.let {
                _book.value = it
                _isLoading.value = false
            }
        }
    }

}