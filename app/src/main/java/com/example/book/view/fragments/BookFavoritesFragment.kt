package com.example.book.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.book.R
import com.example.book.viewmodel.BookFavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFavoritesFragment : Fragment(R.layout.book_favorites_fragment) {

    companion object {
        fun newInstance() = BookFavoritesFragment()
    }

    private lateinit var viewModel: BookFavoritesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookFavoritesViewModel::class.java)


    }

}