package com.example.book.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.book.R
import com.example.book.adapter.BookFavoritesAdapter
import com.example.book.databinding.BookFavoritesFragmentBinding
import com.example.book.model.Book
import com.example.book.view.activities.BookDetailsActivity
import com.example.book.viewmodel.BookFavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFavoritesFragment : Fragment(R.layout.book_favorites_fragment) {

    companion object {
        fun newInstance() = BookFavoritesFragment
    }

    private lateinit var binding: BookFavoritesFragmentBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: BookFavoritesViewModel
    private var adapter = BookFavoritesAdapter() { book ->
        val intentToDetails =
            Intent(activity?.applicationContext, BookDetailsActivity::class.java)
        intentToDetails.putExtra("book", book)
        startActivity(intentToDetails)
        swipeRefreshLayout.isRefreshing = false
    }

    private val observerBookFav = Observer<List<String>> { listOfFavs ->
        viewModel.getFavBooksByApi(listOfFavs)
    }

    private val observerBooks = Observer<List<Book>> { listOfBooks ->
        if (listOfBooks.isEmpty()) {
            binding.recyclerViewFavs.visibility = GONE
            binding.emptyBooksTextView.visibility = VISIBLE
            binding.bookAnimation.cancelAnimation()
            binding.bookAnimation.visibility = INVISIBLE
        } else {
            adapter.refesh(listOfBooks)
            binding.bookAnimation.cancelAnimation()
            binding.bookAnimation.visibility = INVISIBLE
            binding.swipeContainer.visibility = VISIBLE
            binding.recyclerViewFavs.visibility = VISIBLE
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookFavoritesViewModel::class.java)
        binding = BookFavoritesFragmentBinding.bind(view)
        startRecyclerView()
        startViewModel()
        startSwipe()
    }

    private fun startViewModel() {
        viewModel.booksFavs.observe(viewLifecycleOwner, observerBookFav)
        viewModel.books.observe(viewLifecycleOwner, observerBooks)
        viewModel.fetchAllBooksFav()
    }

    private fun startRecyclerView() {
        swipeRefreshLayout = binding.swipeContainer
        binding.recyclerViewFavs.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavs.adapter = adapter
    }

    private fun loadFavBook() {
        swipeRefreshLayout.isRefreshing = false
        viewModel.fetchAllBooksFav()
    }

    private fun startSwipe() {

        swipeRefreshLayout.apply {
            setColorSchemeColors(requireContext().getColor(R.color.white))
            setProgressBackgroundColorSchemeColor(requireContext().getColor(R.color.brown_medium))

            setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = false
                loadFavBook()
                Toast.makeText(requireContext(), "Carregando favoritos...", Toast.LENGTH_SHORT).show()
            }
        }
    }

}