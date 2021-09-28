package com.example.book.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.book.R
import com.example.book.databinding.HomeActivityBinding
import com.example.book.utils.checkForInternet
import com.example.book.utils.replaceFragment
import com.example.book.view.fragments.BookFavoritesFragment
import com.example.book.view.fragments.BookListingFragment
import com.example.book.view.fragments.BookSearchFragment
import com.example.book.view.fragments.ProfileEditFragment
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
                    R.id.home -> {
                        if (checkForInternet(context)) {
                            replaceFragment(
                                BookListingFragment.newInstance(),
                                R.id.containerHome
                            )
                        } else {
                            Intent(context, NoInternetActivity::class.java).apply {
                                startActivity(this)
                            }
                        }
                    }

                    R.id.search -> {
                        if (checkForInternet(context)) {
                            replaceFragment(
                                BookSearchFragment.newInstance(),
                                R.id.containerHome
                            )
                        } else {
                            Intent(context, NoInternetActivity::class.java).apply {
                                startActivity(this)
                            }
                        }
                    }
                    R.id.favorites -> {
                        if (checkForInternet(context)) {
                            replaceFragment(
                                BookFavoritesFragment(),
                                R.id.containerHome
                            )
                        } else {
                            Intent(context, NoInternetActivity::class.java).apply {
                                startActivity(this)
                            }
                        }

                        }
                        R.id.user -> {
                            if (checkForInternet(context)) {
                                replaceFragment(
                                    ProfileEditFragment(),
                                    R.id.containerHome
                                )
                            } else {
                                Intent(context, NoInternetActivity::class.java).apply {
                                    startActivity(this)
                                    finish()
                                }
                            }
                        }
                    }
                    true
                }

        }

    }
}
