package com.example.book.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

const val COLLECTION_USERS = "usersNames"

class AuthenticationRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) {

    fun singEmailAndPassword(
        email: String,
        password: String,
        callback: (FirebaseUser?, String?) -> Unit
    ) {
        val task = auth.signInWithEmailAndPassword(email, password)
        task.addOnSuccessListener {
            if (it.user != null) {
                callback(it.user, null)
            } else {
                callback(null, "Error de Login")
            }

        }

        task.addOnFailureListener { exception ->
            callback(null, exception.message)
        }
    }

    fun createAccount(
        email: String,
        password: String,
        name: String,
        callback: (FirebaseUser?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val task =
                    fireStore.collection(COLLECTION_USERS).document(authResult.user!!.uid).set(
                        hashMapOf(
                            "name" to name
                        )
                    )

                task.addOnSuccessListener {
                    callback(authResult.user)
                }
            }
            .addOnFailureListener {
                it.message
            }
    }

    fun currentUserName(callback: (String) -> Unit) {
        val task = fireStore.collection(COLLECTION_USERS).document(currentUser()!!.uid).get()

        task.addOnSuccessListener { documentSnapshot ->
            documentSnapshot["name"].let {
                callback(it as String)
            }
        }
    }


    fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signOut() {
        auth.signOut()
    }
}
