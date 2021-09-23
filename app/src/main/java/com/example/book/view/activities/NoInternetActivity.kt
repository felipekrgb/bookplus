package com.example.book.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.book.R
import com.example.book.view.fragments.NoInternetFragment

class NoInternetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.no_internet_activity)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, NoInternetFragment())
            .commitNow()
    }
}