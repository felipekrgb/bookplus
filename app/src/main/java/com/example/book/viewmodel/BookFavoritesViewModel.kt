package com.example.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.book.repository.UserBookPreferenceRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookFavoritesViewModel @Inject constructor(
    private val repository: UserBookPreferenceRepository,
) :
    ViewModel() {

    private val _booksFavs = MutableLiveData<List<String>>()
    val booksFavs: LiveData<List<String>> = _booksFavs

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
}