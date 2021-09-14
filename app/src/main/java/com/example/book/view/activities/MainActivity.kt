package com.example.book.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.book.R
import com.example.book.view.fragments.IntroductionFragment
import com.example.book.view.fragments.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, IntroductionFragment())
            .commitNow()
    }
}