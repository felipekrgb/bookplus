package com.example.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book.repository.AuthenticationRepository
import com.example.book.repository.UserCategoriesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userCategoriesRepository: UserCategoriesRepository,
) :
    ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun signInEmailAndPassword(email: String, password: String) {
        authenticationRepository.singEmailAndPassword(email, password) { userFirebase, error ->
            if (userFirebase != null) {
                _user.value = userFirebase
            } else {
                _error.value = error ?: "Erro de Login"
            }
        }
    }
}