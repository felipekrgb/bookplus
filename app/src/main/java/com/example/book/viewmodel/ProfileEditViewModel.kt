package com.example.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book.model.UserCategories
import com.example.book.repository.AuthenticationRepository
import com.example.book.repository.UserCategoriesRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userCategoriesRepository: UserCategoriesRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _userCategories = MutableLiveData<UserCategories>()
    val userCategories: LiveData<UserCategories> = _userCategories

    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser> = _user

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    fun getUserCategories(userId: String) {
        viewModelScope.launch {
            userCategoriesRepository.getUserCategories(userId).apply {
                _userCategories.value = this
            }
        }
    }

    fun updateCategories(userCategories: UserCategories) {
        viewModelScope.launch {
            userCategoriesRepository.updateUserCategories(userCategories)
        }
    }

    fun updateName(name: String) {
        viewModelScope.launch {
            authenticationRepository.updateUserName(name)
        }
    }

    fun getCurrentUser() {
        authenticationRepository.currentUser()?.apply {
            _user.value = this
        }
    }

    fun getCurrentUserName() {
        authenticationRepository.currentUserName {
            _userName.value = it
        }
    }

}