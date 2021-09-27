package com.example.book.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.book.R
import com.example.book.databinding.HomeActivityBinding
import com.example.book.utils.checkForInternet
import com.example.book.utils.replaceFragment
import com.example.book.view.fragments.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkForInternet(this)) {
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
                                    finish()
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
                                    finish()
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
                                    finish()
                                }
                            }

                        }
                        R.id.user -> {
                            if (checkForInternet(context)) {
                                replaceFragment(
                                    EditProfileFragment(),
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

        } else {
            Intent(applicationContext, NoInternetActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

    }
}
