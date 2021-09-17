package com.example.book.view.fragments

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
import com.example.book.view.dialogs.BasicDetailsFragment
import com.example.book.viewmodel.BookFavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFavoritesFragment : Fragment(R.layout.book_favorites_fragment) {

    companion object {
        fun newInstance() = BookFavoritesFragment
    }

    private lateinit var viewModel: BookFavoritesViewModel
    private var adapter = BookFavoritesAdapter() {
        BasicDetailsFragment.newInstance(it).let {
            it.show(parentFragmentManager, "dialog_basic_details")
        }
    }
    private lateinit var binding: BookFavoritesFragmentBinding

    private val observerBookFav = Observer<List<String>> { listOfFavs ->
        viewModel.getFavBooksByApi(listOfFavs)
    }

    private val observerBooks = Observer<List<Book>> { listOfBooks ->
        adapter.refesh(listOfBooks)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookFavoritesViewModel::class.java)
        binding = BookFavoritesFragmentBinding.bind(view)

        startRecyclerView()
        startViewModel()

    }

    private fun startViewModel() {
        viewModel.booksFavs.observe(viewLifecycleOwner, observerBookFav)
        viewModel.books.observe(viewLifecycleOwner, observerBooks)
        viewModel.fetchAllBooksFav()
    }

    private fun startRecyclerView() {
        binding.recyclerViewFavs.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavs.adapter = adapter
    }

}