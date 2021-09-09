package com.example.book.utils

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.book.R

fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    @IdRes idComponent: Int = R.id.container
) {
    supportFragmentManager.beginTransaction()
        .replace(idComponent, fragment)
        .commitNow()
}