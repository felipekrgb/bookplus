package com.example.book.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.book.R
import com.example.book.databinding.HomeActivityBinding
import com.example.book.utils.replaceFragment
import com.example.book.view.fragments.BookFavoritesFragment
import com.example.book.view.fragments.BookListingFragment
import com.example.book.view.fragments.BookSearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(BookListingFragment.newInstance(), R.id.containerHome)

        binding.bottomNav.apply {

            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> replaceFragment(
                        BookListingFragment.newInstance(),
                        R.id.containerHome
                    )
                    R.id.search -> replaceFragment(
                        BookSearchFragment.newInstance(),
                        R.id.containerHome
                    )
                    R.id.favorites -> replaceFragment(
                        BookFavoritesFragment(),
                        R.id.containerHome
                    )
                }
                true
            }

        }

    }
}