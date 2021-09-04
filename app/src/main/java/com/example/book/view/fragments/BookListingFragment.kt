package com.example.book.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.book.R
import com.example.book.viewmodel.BookListingViewModel

class BookListingFragment : Fragment() {

    companion object {
        fun newInstance() = BookListingFragment()
    }

    private lateinit var viewModel: BookListingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.book_listing_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookListingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}