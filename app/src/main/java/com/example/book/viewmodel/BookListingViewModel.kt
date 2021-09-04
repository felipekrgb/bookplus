package com.example.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.book.model.Book
import com.example.book.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookListingViewModel @Inject constructor(

    private val repository: BooksRepository

) : ViewModel() {


    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getBooksByTerms(terms: String) {

        repository.getBooksByTerms(terms) { response, error ->

            response?.let {
                _books.value = it
            }
            error?.let {
                _error.value = it
            }

        }
    }

}