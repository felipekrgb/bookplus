package com.example.book.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.book.R
import com.example.book.adapter.BookFavoritesAdapter
import com.example.book.databinding.BookFavoritesFragmentBinding
import com.example.book.model.Book
import com.example.book.view.activities.BookDetailsActivity
import com.example.book.view.dialogs.BasicDetailsFragment
import com.example.book.viewmodel.BookFavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFavoritesFragment : Fragment(R.layout.book_favorites_fragment) {

    companion object {
        fun newInstance() = BookFavoritesFragment
    }

    private lateinit var viewModel: BookFavoritesViewModel
    private var adapter = BookFavoritesAdapter() { book ->
        val intentToDetails =
            Intent(activity?.applicationContext, BookDetailsActivity::class.java)
        intentToDetails.putExtra("book", book)
        startActivity(intentToDetails)
    }
    private lateinit var binding: BookFavoritesFragmentBinding

    private val observerBookFav = Observer<List<String>> { listOfFavs ->
        viewModel.getFavBooksByApi(listOfFavs)
    }

    private val observerBooks = Observer<List<Book>> { listOfBooks ->
        if (listOfBooks.isEmpty()) {
            binding.emptyBooksTextView.visibility = View.VISIBLE
        } else {
            binding.recyclerViewFavs.visibility = View.VISIBLE
            adapter.refesh(listOfBooks)
        }
    }

    private val observerLoading = Observer<Boolean> { isLoading ->
        if (isLoading) {
            binding.bookSearchAnimation.visibility = View.VISIBLE
            binding.bookSearchAnimation.playAnimation()
        } else {
            binding.recyclerViewFavs.visibility = View.VISIBLE
            binding.bookSearchAnimation.visibility = View.INVISIBLE
            binding.bookSearchAnimation.cancelAnimation()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookFavoritesViewModel::class.java)
        binding = BookFavoritesFragmentBinding.bind(view)

        startRecyclerView()
        startViewModel()

    }

    private fun startViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner, observerLoading)
        viewModel.booksFavs.observe(viewLifecycleOwner, observerBookFav)
        viewModel.books.observe(viewLifecycleOwner, observerBooks)
        viewModel.fetchAllBooksFav()
    }

    private fun startRecyclerView() {
        binding.recyclerViewFavs.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavs.adapter = adapter
    }

}