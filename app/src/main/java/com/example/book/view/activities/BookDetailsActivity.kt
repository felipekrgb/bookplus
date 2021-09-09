package com.example.book.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.book.R
import com.example.book.databinding.BookDetailsActivityBinding

class BookDetailsActivity : AppCompatActivity() {

    lateinit var binding: BookDetailsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_details_activity)

        val book = intent.getSerializableExtra("book")

    }
}