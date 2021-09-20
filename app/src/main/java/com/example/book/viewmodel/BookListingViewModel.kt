package com.example.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book.model.Book
import com.example.book.repository.AuthenticationRepository
import com.example.book.repository.BooksRepository
import com.example.book.repository.UserCategoriesRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListingViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val userCategoriesRepository: UserCategoriesRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {


    private val _books = MutableLiveData<HashMap<String, List<Book>>>()
    val books: LiveData<HashMap<String, List<Book>>> = _books

    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser> = _user

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    fun getBooksByTerms(terms: List<String>, startIndex: Int = 0) {
        viewModelScope.launch {
            terms.forEach { term ->
                booksRepository.getBooksByTerms(term.replace(" ", "+"), startIndex)?.let { books ->
                    _books.value = hashMapOf(term to books)
                }
            }
        }
    }

    fun getUserCategories(userId: String) {
        viewModelScope.launch {
            userCategoriesRepository.getUserCategories(userId)?.let { userCategories ->
                _categories.value = userCategories.categories
            }
        }
    }

    fun getCurrentUser() {
        authenticationRepository.currentUser()?.apply {
            _user.value = this
        }
    }
}