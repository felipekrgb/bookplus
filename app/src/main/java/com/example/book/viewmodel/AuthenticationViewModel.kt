package com.example.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.book.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val repository: AuthenticationRepository) :
    ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun signInEmailAndPassword(email: String, password: String) {
        repository.singEmailAndPassword(email, password) { userFirebase, error ->
            if (userFirebase != null) {
                _user.value = userFirebase
            } else {
                _error.value = error ?: "Erro de Login"
            }
        }
    }

    fun signUpEmailAndPassword(email: String, password: String, user: String) {
        repository.createAccount(email, password, user) {
            _user.value = it
        }
    }
}