package com.example.book.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.book.R
import com.example.book.view.activities.NoInternetActivity
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    @IdRes idComponent: Int = R.id.container
) {
    supportFragmentManager.beginTransaction()
        .replace(idComponent, fragment)
        .commitNow()
}

fun AppCompatActivity.hideKeyboard() {
    val imm = window.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun AppCompatActivity.snackBar(view: View, @StringRes msgId: Int, @ColorRes colorId: Int) {
    hideKeyboard()

    setupSnackbar(view, msgId, colorId).apply {
        this.show()
    }
}

private fun AppCompatActivity.setupSnackbar(
    v: View,
    @StringRes msgId: Int,
    @ColorRes colorId: Int,
): Snackbar {
    return Snackbar.make(v, msgId, Snackbar.LENGTH_LONG).apply {
        view.setBackgroundColor(getColor(colorId))

        anchorView = v

        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            setTextColor(getColor(R.color.white))
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        show()
    }
}

fun AppCompatActivity.checkForInternet(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val network = connectivityManager.activeNetwork ?: return false

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {

            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {

        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}

fun Fragment.goToNoInternetActivity() {
    Intent(requireActivity(), NoInternetActivity::class.java).apply {
        startActivity(this)
    }
}

