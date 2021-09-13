package com.example.book.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val authent: FirebaseAuth) {


    fun singEmailAndPassword(
        email: String,
        password: String,
        callback: (FirebaseUser?, String?) -> Unit
    ) {
        val tesk = authent.signInWithEmailAndPassword(email, password)
        tesk.addOnSuccessListener {
            if (it.user != null) {
                callback(it.user, null)
            } else {
                callback(null, "Error de Login")
            }

        }

        tesk.addOnFailureListener { exception ->
            callback(null, exception.message)
        }
    }

    fun creatAccount(
        email: String,
        password: String,
        callback: (FirebaseUser?) -> Unit
    ) {
        authent.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                callback(authResult.user)
            }
            .addOnFailureListener {
                it.message
            }
    }

    fun signOut() {
        authent.signOut()
    }

    fun currentUser(): FirebaseUser? = authent.currentUser
}
