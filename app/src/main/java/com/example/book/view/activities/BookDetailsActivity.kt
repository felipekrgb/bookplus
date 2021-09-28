package com.example.book.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.book.R
import com.example.book.databinding.BookDetailsActivityBinding
import com.example.book.model.Book
import com.example.book.utils.checkForInternet
import com.example.book.utils.replaceFragment
import com.example.book.utils.snackBar
import com.example.book.view.fragments.BookDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailsActivity : AppCompatActivity() {

    lateinit var binding: BookDetailsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val book = intent.getSerializableExtra("book") as Book

        if (checkForInternet(this)) {
            replaceFragment(BookDetailsFragment.newInstance(book.id), R.id.containerDetails)

        } else {
            showSnackbar(R.string.no_conection, R.color.red)
            Intent(applicationContext, NoInternetActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val view = binding.root
        snackBar(view, msgId, colorId)
    }
}