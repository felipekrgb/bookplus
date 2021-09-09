package com.example.book.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.book.R
import com.example.book.databinding.BookSearchFragmentBinding
import com.example.book.viewmodel.BookSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookSearchFragment : Fragment(R.layout.book_search_fragment) {

    companion object {
        fun newInstance() = BookSearchFragment()
    }

    private lateinit var binding: BookSearchFragmentBinding
    private lateinit var viewModel: BookSearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookSearchFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(BookSearchViewModel::class.java)

    }
}