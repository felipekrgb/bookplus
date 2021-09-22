package com.example.book.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.book.R
import com.example.book.databinding.MainActivityBinding
import com.example.book.utils.replaceFragment
import com.example.book.view.fragments.CategoryChooserFragment
import com.example.book.view.fragments.IntroductionFragment
import com.example.book.view.fragments.SignInFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (FirebaseAuth.getInstance().currentUser != null) {
            Intent(this, HomeActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        } else {
            replaceFragment(IntroductionFragment())
        }
    }
}