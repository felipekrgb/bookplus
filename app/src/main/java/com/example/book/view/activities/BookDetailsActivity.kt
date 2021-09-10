package com.example.book.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.book.R
import com.example.book.databinding.BookDetailsActivityBinding
import com.example.book.model.Book
import com.example.book.utils.replaceFragment
import com.example.book.view.fragments.BookDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_details_activity)

        val book = intent.getSerializableExtra("book") as Book

        replaceFragment(BookDetailsFragment.newInstance(book.id))

    }
}