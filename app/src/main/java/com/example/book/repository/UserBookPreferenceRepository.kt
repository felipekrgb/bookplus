package com.example.book.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

const val COLLECTION_FAVBOOK = "booksFavorites"

class UserBookPreferenceRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    fun saveBooks(uid: String, idBook: String) {
        val task = firestore.collection(COLLECTION_FAVBOOK)
            .document(uid)
            .get()
        task.addOnSuccessListener { documentSnapshot ->

            val favorites: ArrayList<Any> =
                documentSnapshot["favorites"]?.let { it as ArrayList<Any> }
                    ?: arrayListOf()

            if (!favorites.contains(idBook)) {
                updateFavoriteBooks(uid, favorites.apply {
                    add(idBook)
                })
            }
        }

    }

    private fun updateFavoriteBooks(uid: String, ids: ArrayList<Any>) {
        firestore.collection(COLLECTION_FAVBOOK)
            .document(uid)
            .set(
                hashMapOf(
                    "favorites" to ids
                ), SetOptions.merge()
            ).addOnSuccessListener { result ->
                println("Success")
            }
            .addOnFailureListener { exception ->
                println(exception.message)
            }
    }

    fun delete(uid: String, idBook: String) {
        val task = firestore.collection(COLLECTION_FAVBOOK)
            .document(uid)
            .get()
        task.addOnSuccessListener { documentSnapshot ->

            val favorites: ArrayList<Any> =
                documentSnapshot["favorites"]?.let { it as ArrayList<Any> }
                    ?: arrayListOf()
            if (favorites.contains(idBook)) {
                updateFavoriteBooks(uid, favorites.apply {
                    remove(idBook)
                })
            }
        }

    }

    fun fetchAllBooks(uid: String, callback: (ArrayList<Any>) -> Unit) {
        firestore.collection(COLLECTION_FAVBOOK)
            .document(uid)
            .get()
            .addOnSuccessListener {
                callback(it["favorites"]?.let { it as ArrayList<Any> }
                    ?: arrayListOf())
            }
    }
}