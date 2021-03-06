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

    private val _categories = MutableLiveData<List<String>?>()
    val categories: LiveData<List<String>?> = _categories

    private val _isSigned = MutableLiveData(true)
    val isSignedIn: LiveData<Boolean> = _isSigned

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

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
            val userCategories = userCategoriesRepository.getUserCategories(userId)
            if (userCategories != null) {
                _categories.value = userCategories.categories
            } else {
                _categories.value = null
            }
        }
    }

    fun getCurrentUserName() {
        authenticationRepository.currentUserName {
            _userName.value = it
        }
    }

    fun getCurrentUser() {
        authenticationRepository.currentUser()?.apply {
            _user.value = this
        }
    }

    fun signOut() {
        authenticationRepository.signOut()
        _isSigned.value = false
    }
}