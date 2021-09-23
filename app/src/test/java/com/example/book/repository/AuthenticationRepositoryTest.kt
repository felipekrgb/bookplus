package com.example.book.repository

import android.app.Activity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

@RunWith(JUnit4::class)
class AuthenticationRepositoryTest {

    private lateinit var successTask: Task<AuthResult>
    private lateinit var failureTask: Task<AuthResult>

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fireStore: FirebaseFirestore

    private lateinit var authenticationRepositoryTest: AuthenticationRepository

    private var logInResult = UNDEF

    companion object {
        private const val SUCCESS = 1
        private const val FAILURE = -1
        private const val UNDEF = 0
    }

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        successTask = object : Task<AuthResult>() {
            override fun isComplete(): Boolean {
                return true
            }

            override fun isSuccessful(): Boolean {
                return true
            }

            override fun addOnCompleteListener(
                    executor: Executor,
                    onCompleteListener: OnCompleteListener<AuthResult>
            ): Task<AuthResult> {
                onCompleteListener.onComplete(failureTask)
                return failureTask
            }

            override fun isCanceled(): Boolean {
                return true
            }

            override fun getResult(): AuthResult? {
                TODO("Not yet implemented")
            }

            override fun <X : Throwable?> getResult(p0: Class<X>): AuthResult? {
                TODO("Not yet implemented")
            }

            override fun getException(): Exception? {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(p0: OnSuccessListener<in AuthResult>): Task<AuthResult> {
                return this
            }

            override fun addOnSuccessListener(p0: Executor, p1: OnSuccessListener<in AuthResult>): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(p0: Activity, p1: OnSuccessListener<in AuthResult>): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(p0: OnFailureListener): Task<AuthResult> {
                return this
            }

            override fun addOnFailureListener(p0: Executor, p1: OnFailureListener): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(p0: Activity, p1: OnFailureListener): Task<AuthResult> {
                TODO("Not yet implemented")
            }

        }

        failureTask = object : Task<AuthResult>() {
            override fun isComplete(): Boolean {
                return true
            }

            override fun isSuccessful(): Boolean {
                return false
            }

            override fun addOnCompleteListener(
                    executor: Executor,
                    onCompleteListener: OnCompleteListener<AuthResult>
            ): Task<AuthResult> {
                onCompleteListener.onComplete(failureTask)
                return failureTask
            }

            override fun isCanceled(): Boolean {
                return false
            }

            override fun getResult(): AuthResult? {
                return result
            }

            override fun <X : Throwable?> getResult(p0: Class<X>): AuthResult? {
                return result
            }

            override fun getException(): Exception? {
                return exception
            }

            override fun addOnSuccessListener(p0: OnSuccessListener<in AuthResult>): Task<AuthResult> {
                return this
            }

            override fun addOnSuccessListener(p0: Executor, p1: OnSuccessListener<in AuthResult>): Task<AuthResult> {
                return this
            }

            override fun addOnSuccessListener(p0: Activity, p1: OnSuccessListener<in AuthResult>): Task<AuthResult> {
                return this
            }

            override fun addOnFailureListener(p0: OnFailureListener): Task<AuthResult> {
                return this
            }

            override fun addOnFailureListener(p0: Executor, p1: OnFailureListener): Task<AuthResult> {
                return this
            }

            override fun addOnFailureListener(p0: Activity, p1: OnFailureListener): Task<AuthResult> {
                return this
            }

        }

        authenticationRepositoryTest = AuthenticationRepository(firebaseAuth, fireStore)
    }

//    @Test
//    fun liginInSucess_test() {
//        val email = "cool@cool.com"
//        val password = "123456"
//        Mockito.`when`(firebaseAuth.signInWithEmailAndPassword(email, password))
//                .thenReturn(successTask)
//        authenticationRepositoryTest.singEmailAndPassword(email,password)
//        Truth.assertThat(logInResult).isEqualTo(SUCCESS)
//    }


}