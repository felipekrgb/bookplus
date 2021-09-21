package com.example.book.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.book.R
import com.example.book.adapter.BookAdapter
import com.example.book.databinding.BookListingFragmentBinding
import com.example.book.model.Book
import com.example.book.view.activities.MainActivity
import com.example.book.view.dialogs.BasicDetailsFragment
import com.example.book.viewmodel.BookListingViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookListingFragment : Fragment(R.layout.book_listing_fragment) {

    companion object {
        fun newInstance() = BookListingFragment()
    }

    private lateinit var binding: BookListingFragmentBinding
    private lateinit var viewModel: BookListingViewModel
    private lateinit var bookFirstCategoryRecyclerView: RecyclerView
    private lateinit var bookSecondCategoryRecyclerView: RecyclerView
    private lateinit var bookThirdCategoryRecyclerView: RecyclerView
    private var categories = mutableListOf<String>()
    private var booksListed = 0
    private var adapterFirstCategory = BookAdapter() {
        BasicDetailsFragment.newInstance(it).show(parentFragmentManager, "dialog_basic_details")
    }
    private var adapterSecondCategory = BookAdapter() {
        BasicDetailsFragment.newInstance(it).show(parentFragmentManager, "dialog_basic_details")
    }
    private var adapterThirdCategory = BookAdapter() {
        BasicDetailsFragment.newInstance(it).show(parentFragmentManager, "dialog_basic_details")
    }

    private val observerSignedUser = Observer<FirebaseUser> { user ->
        viewModel.getUserCategories(user.uid)
    }

    private val observerSignOut = Observer<Boolean> {
        if (!it) {
            Intent(requireActivity(), MainActivity::class.java).apply {
                startActivity(this)
            }
            requireActivity().finish()
        }
    }

    private val observerCategories = Observer<List<String>> { categories ->
        this.categories.addAll(categories)
        viewModel.getBooksByTerms(categories)


        binding.bookFirstCategoryTextView.text = categories[0]
        binding.bookSecondCategoryTextView.text = categories[1]
        binding.bookThirdCategoryTextView.text = categories[2]
    }

    private val observerBooks = Observer<HashMap<String, List<Book>>> { hashMap ->
        val key = hashMap.keys.map { it }[0]
        val finalKey = categories.filter { it == key }[0]
        if (finalKey == categories[0]) {
            adapterFirstCategory.update(hashMap[finalKey]?.map { it })
        } else if (finalKey == categories[1]) {
            adapterSecondCategory.update(hashMap[finalKey]?.map { it })
        } else if (finalKey == categories[2]) {
            adapterThirdCategory.update(hashMap[finalKey]?.map { it })
        }

        booksListed++

        if (booksListed == 3) {
            binding.booksLoadingAnimation.visibility = View.GONE
            binding.booksLoadingAnimation.cancelAnimation()
            binding.container.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookListingFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(BookListingViewModel::class.java)

        viewModel.user.observe(viewLifecycleOwner, observerSignedUser)
        viewModel.categories.observe(viewLifecycleOwner, observerCategories)

        setupRecyclerView()
        setupObservers()
        setupButtons()

        viewModel.getCurrentUser()
    }

    private fun setupButtons() {
        binding.signOutImage.setOnClickListener {
            viewModel.signOut()
        }
    }

    private fun setupRecyclerView() {
        bookFirstCategoryRecyclerView = binding.bookFirstCategoryRecyclerView
        bookFirstCategoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bookFirstCategoryRecyclerView.adapter = adapterFirstCategory

        bookSecondCategoryRecyclerView = binding.bookSecondCategoryRecyclerView
        bookSecondCategoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bookSecondCategoryRecyclerView.adapter = adapterSecondCategory

        bookThirdCategoryRecyclerView = binding.bookThirdCategoryRecyclerView
        bookThirdCategoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bookThirdCategoryRecyclerView.adapter = adapterThirdCategory
    }

    private fun setupObservers() {
        viewModel.books.observe(viewLifecycleOwner, observerBooks)
        viewModel.isSignedIn.observe(viewLifecycleOwner, observerSignOut)
    }
}