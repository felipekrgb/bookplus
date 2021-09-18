package com.example.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.book.repository.AuthenticationRepository
import com.example.book.repository.UserCategoriesRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userCategoriesRepository: UserCategoriesRepository,
) :
    ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    fun signUpEmailAndPassword(email: String, password: String, user: String) {
        authenticationRepository.createAccount(email, password, user) {
            _user.value = it
        }
    }
}