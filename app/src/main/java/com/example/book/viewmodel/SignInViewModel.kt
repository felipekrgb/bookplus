package com.example.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.book.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: AuthenticationRepository) :
    ViewModel() {

    private val _isSignedIn = MutableLiveData<Boolean>(true)
    val isSignedIn: LiveData<Boolean> = _isSignedIn

    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser> = _user

    fun getCorrentUser() {
        repository.currentUser()?.apply {
            _user.value = this
        }
    }

    fun signOut() {
        repository.signOut()
        _isSignedIn.value = false
    }
}