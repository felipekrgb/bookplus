package com.example.book.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.book.R
import com.example.book.view.fragments.SignInFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignInFragment.newInstance())
                .commitNow()
        }
    }
}