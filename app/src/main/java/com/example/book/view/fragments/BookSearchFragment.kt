package com.example.book.view.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.book.R
import com.example.book.adapter.BookSearchAdapter
import com.example.book.databinding.BookSearchFragmentBinding
import com.example.book.model.Book
import com.example.book.utils.hideKeyboard
import com.example.book.view.dialogs.BasicDetailsFragment
import com.example.book.viewmodel.BookSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookSearchFragment : Fragment(R.layout.book_search_fragment) {

    companion object {
        fun newInstance() = BookSearchFragment()
    }

    private lateinit var binding: BookSearchFragmentBinding
    private lateinit var viewModel: BookSearchViewModel
    private var adapter = BookSearchAdapter() {
        BasicDetailsFragment.newInstance(it).let {
            it.show(parentFragmentManager, "dialog_basic_details")
        }
    }

    private val observerBooksSearch = Observer<List<Book>> {
        if (it.isNullOrEmpty()) {
            binding.emptyBooksTextView.visibility = View.VISIBLE
        } else {
            binding.recyclerViewBookSearch.visibility = View.VISIBLE
            adapter.refesh(it)
        }
    }

    private val observerErrorSearch = Observer<String> {
        println(it)
    }

    private val observerLoading = Observer<Boolean> { isLoading ->
        if (isLoading) {
            binding.bookSearchImageView.visibility = View.GONE
            binding.bookSearchAnimation.visibility = View.VISIBLE
            binding.bookSearchAnimation.playAnimation()
        } else {
            binding.bookSearchAnimation.cancelAnimation()
            binding.bookSearchAnimation.visibility = View.INVISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookSearchFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(BookSearchViewModel::class.java)

        startRecyclerView()
        startObserver()
        startSettingsSearch()

    }

    private fun startSettingsSearch() {
        binding.editTextSearch.editText?.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                binding.emptyBooksTextView.visibility = View.GONE
                binding.recyclerViewBookSearch.visibility = View.GONE
                viewModel.getBooksByTerm(binding.editTextSearch.editText?.text.toString())
                (requireActivity() as AppCompatActivity).hideKeyboard()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun startObserver() {
        viewModel.books.observe(viewLifecycleOwner, observerBooksSearch)
        viewModel.error.observe(viewLifecycleOwner, observerErrorSearch)
        viewModel.isLoading.observe(viewLifecycleOwner, observerLoading)
    }

    private fun startRecyclerView() {
        binding.recyclerViewBookSearch.adapter = adapter
        binding.recyclerViewBookSearch.layoutManager = LinearLayoutManager(requireContext())
    }
}