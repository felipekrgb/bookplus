package com.example.book.view.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.book.R
import com.example.book.adapter.BookSearchAdapter
import com.example.book.databinding.BookSearchFragmentBinding
import com.example.book.model.Book
import com.example.book.utils.checkForInternet
import com.example.book.utils.goToNoInternetActivity
import com.example.book.utils.hideKeyboard
import com.example.book.utils.snackBar
import com.example.book.view.activities.HomeActivity
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var searchTerm: String? = null
    private var didSearchTermChanged = false
    private var adapter = BookSearchAdapter() {
        BasicDetailsFragment.newInstance(it).show(parentFragmentManager, "dialog_basic_details")
    }

    private val observerBooksSearch = Observer<List<Book>> {
        if (it.isNullOrEmpty()) {
            binding.emptyBooksTextView.visibility = View.VISIBLE
        } else {
            binding.swipeContainer.visibility = View.VISIBLE
            binding.recyclerViewBookSearch.visibility = View.VISIBLE
            if (didSearchTermChanged) {
                didSearchTermChanged = false
                adapter.update(it, true)
                recyclerView.scrollToPosition(0)
            } else {
                adapter.update(it)
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private val observerLoading = Observer<Boolean> { isLoading ->
        if (isLoading && didSearchTermChanged) {
            binding.bookSearchImageView.visibility = View.GONE
            binding.bookSearchAnimation.visibility = View.VISIBLE
            binding.bookSearchAnimation.playAnimation()
        } else {
            binding.bookSearchAnimation.cancelAnimation()
            binding.bookSearchAnimation.visibility = View.INVISIBLE
        }
    }

    private val observerStartIndex = Observer<Int> { startIndex ->
        if (!searchTerm.isNullOrEmpty()) {
            if ((requireActivity() as HomeActivity).checkForInternet(requireContext())) {
                viewModel.getBooksByTerm(searchTerm!!, startIndex)
            } else {
                goToNoInternetActivity()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookSearchFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(BookSearchViewModel::class.java)

        swipeRefreshLayout = binding.swipeContainer
        setupRecyclerView()
        setupObservers()
        setupSettingsSearch()
        setupEventsForRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewBookSearch
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.books.observe(viewLifecycleOwner, observerBooksSearch)
        viewModel.isLoading.observe(viewLifecycleOwner, observerLoading)
        viewModel.startIndex.observe(viewLifecycleOwner, observerStartIndex)

    }

    private fun setupSettingsSearch() {
        binding.editTextSearch.editText?.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                searchTerm = binding.editTextSearch.editText?.text.toString()
                if (searchTerm.isNullOrEmpty()) {
                    showSnackbar(R.string.no_search_term, R.color.red)
                } else {
                    if ((requireActivity() as HomeActivity).checkForInternet(requireContext())) {
                        didSearchTermChanged = true
                        binding.emptyBooksTextView.visibility = View.GONE
                        binding.recyclerViewBookSearch.visibility = View.GONE
                        viewModel.getBooksByTerm(searchTerm!!)
                        (requireActivity() as AppCompatActivity).hideKeyboard()
                    } else {
                        goToNoInternetActivity()
                    }
                }
                return@OnKeyListener true
            }
            false
        })
    }

    private fun setupEventsForRecyclerView() {
        binding.recyclerViewBookSearch.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if ((requireActivity() as HomeActivity).checkForInternet(requireContext())) {
                        binding.swipeContainer.isRefreshing = true
                        viewModel.nextBooks()
                    } else {
                        goToNoInternetActivity()
                    }
                }
            }
        })

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
        }

        binding.swipeContainer.apply {
            setColorSchemeColors(requireContext().getColor(R.color.white))
            setProgressBackgroundColorSchemeColor(requireContext().getColor(R.color.brown_medium))
        }
    }

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val activity = requireActivity() as HomeActivity
        val bottomNav = activity.binding.bottomNav
        activity.snackBar(bottomNav, msgId, colorId)
    }
}