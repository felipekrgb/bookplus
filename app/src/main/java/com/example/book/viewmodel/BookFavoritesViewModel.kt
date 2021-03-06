package com.example.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book.model.Book
import com.example.book.repository.BooksRepository
import com.example.book.repository.UserBookPreferenceRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookFavoritesViewModel @Inject constructor(
    private val repository: UserBookPreferenceRepository,
    private val booksRepository: BooksRepository,
) :
    ViewModel() {

    private val _booksFavs = MutableLiveData<List<String>>()
    val booksFavs: LiveData<List<String>> = _booksFavs

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    fun save(ids: String) {
        FirebaseAuth.getInstance().currentUser?.let {
            repository.saveBooks(it.uid, ids)
        }
    }

    fun delete(id: String) {
        FirebaseAuth.getInstance().currentUser?.let {
            repository.delete(it.uid, id)
        }

    }

    fun fetchAllBooksFav() {
        FirebaseAuth.getInstance().currentUser?.let {
            repository.fetchAllBooks(it.uid) {
                _booksFavs.value = it as List<String>
            }
        }
    }

    //Funcao criada para realizar a busca do ID do Livro na API.

    fun getFavBooksByApi(listOfFavs: List<String>) {
        val listOfBooks = arrayListOf<Book>()
        viewModelScope.launch {
            listOfFavs.forEach {
                booksRepository.getBookById(it)?.let { book ->
                    listOfBooks.add(book)
                }
            }
            _books.value = listOfBooks
        }
    }
}

