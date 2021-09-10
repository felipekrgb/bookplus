package com.example.book.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.book.R
import com.example.book.databinding.BookDetailsFragmentBinding
import com.example.book.model.Book
import com.example.book.viewmodel.BookDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailsFragment : Fragment(R.layout.book_details_fragment) {

    companion object {
        fun newInstance(id: String): BookDetailsFragment {
            return BookDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString("book_id", id)
                }
            }
        }
    }

    private lateinit var viewModel: BookDetailsViewModel
    private lateinit var binding: BookDetailsFragmentBinding
    private val observerBook = Observer<Book?> {
        bindData(it)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookDetailsFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(BookDetailsViewModel::class.java)
        viewModel.book.observe(viewLifecycleOwner, observerBook)

        val bookId = arguments?.getString("book_id") as String

        viewModel.getBookById(bookId)

    }

    private fun bindData(book: Book?) {

        binding.bookTitleTextView.text = book!!.id

    }

}