package com.example.book.view.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.book.R
import com.example.book.databinding.MainActivityBinding
import com.example.book.utils.checkForInternet
import com.example.book.utils.replaceFragment
import com.example.book.utils.snackBar
import com.example.book.view.fragments.IntroductionFragment
import com.example.book.view.fragments.NoInternetFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(checkForInternet(this)) {
            if (FirebaseAuth.getInstance().currentUser != null) {
                Intent(this, HomeActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            } else {
                replaceFragment(IntroductionFragment())
            }
        } else {
            snackBar(binding.root, R.string.no_conection, R.color.red)
            Intent(this, NoInternetActivity::class.java).apply {
                startActivity(this)
                finish()
            }

        }
    }
}