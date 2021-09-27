package com.example.book.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.book.R
import com.example.book.databinding.NoInternetActivityBinding
import com.example.book.utils.replaceFragment
import com.example.book.view.fragments.NoInternetFragment

class NoInternetActivity : AppCompatActivity() {

    private lateinit var binding: NoInternetActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NoInternetActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(NoInternetFragment(), R.id.container)
    }

    override fun onBackPressed() {
    }
}