package com.example.book.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.book.R
import com.example.book.utils.replaceFragment
import com.example.book.view.fragments.CategoryChooserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_activity)
        replaceFragment(CategoryChooserFragment(), R.id.categoryContainer)
    }
}