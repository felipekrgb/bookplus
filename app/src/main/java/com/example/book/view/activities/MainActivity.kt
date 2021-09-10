package com.example.book.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.book.R
import com.example.book.databinding.MainActivityBinding
import com.example.book.utils.replaceFragment
import com.example.book.view.fragments.BookListingFragment
import com.example.book.view.fragments.BookSearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(BookListingFragment.newInstance())

        binding.bottomNav.apply {

            setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.home -> replaceFragment(BookListingFragment.newInstance())
                    R.id.search -> replaceFragment(BookSearchFragment.newInstance())
                }
                true
            }

        }

    }
}