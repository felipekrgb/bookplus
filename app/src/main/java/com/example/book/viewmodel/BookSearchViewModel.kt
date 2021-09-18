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
class BookSearchViewModel @Inject constructor(
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getBooksByTerm(terms: String) {
        _isLoading.value = true
        viewModelScope.launch {
            booksRepository.getBooksByTerms(terms.replace(" ", "+")).let {
                _books.value = it
                _isLoading.value = false
            }
        }
    }
}