package com.example.book.repository

import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class AuthenticationRepositoryTest {

    @Mock
    private lateinit var task: Task<AuthResult>
    @Mock
    private lateinit var firebaseAuth: FirebaseAuth
    @Mock
    private lateinit var fireStore: FirebaseFirestore
    @Mock
    private lateinit var result: AuthResult
    @Mock
    private lateinit var firebaseUser: FirebaseUser

    private lateinit var authenticationRepository: AuthenticationRepository


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        authenticationRepository = AuthenticationRepository(firebaseAuth, fireStore)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `sign in success`() = runBlocking {
        val email = "cool@cool.com"
        val password = "123456"

        Mockito.`when`(firebaseAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(task)

        Mockito.`when`(task.result).thenReturn(result)
        Mockito.`when`(result.user).thenReturn(firebaseUser)

        assertThat(task.result!!.user).isEqualTo(firebaseUser)
    }

    @Test
    fun `sign in failure`() = runBlocking {
        val email = "cool@cool.com"
        val password = "123456"

        Mockito.`when`(firebaseAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(task)

        Mockito.`when`(task.result).thenReturn(result)
        Mockito.`when`(result.user).thenReturn(null)

        assertThat(task.result!!.user).isEqualTo(null)
    }

}