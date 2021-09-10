package com.example.book.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.book.R
import com.example.book.adapter.BookAdapter
import com.example.book.databinding.BookListingFragmentBinding
import com.example.book.model.Book
import com.example.book.view.dialogs.BasicDetailsFragment
import com.example.book.viewmodel.BookListingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookListingFragment : Fragment(R.layout.book_listing_fragment) {

    companion object {
        fun newInstance() = BookListingFragment()
    }

    private lateinit var binding: BookListingFragmentBinding
    private lateinit var viewModel: BookListingViewModel
    private lateinit var firstCategoryRecyclerView: RecyclerView
    private var adapter = BookAdapter() {
        BasicDetailsFragment.newInstance(it).let {
            it.show(parentFragmentManager, "dialog_basic_details")
        }
    }

    private val observerBooks = Observer<List<Book>> {
        adapter.update(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookListingFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(BookListingViewModel::class.java)

        firstCategoryRecyclerView = binding.bookFirstCategoryRecyclerView
        firstCategoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        firstCategoryRecyclerView.adapter = adapter

        viewModel.books.observe(viewLifecycleOwner, observerBooks)

        viewModel.getBooksByTerms("dog")
    }
}