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
import com.example.book.utils.checkForInternet
import com.example.book.utils.goToNoInternetActivity
import com.example.book.view.activities.CategoryActivity
import com.example.book.view.activities.HomeActivity
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

    private val observerUser = Observer<FirebaseUser> { user ->
        viewModel.getUserCategories(user.uid)
    }

    private val observerSignOut = Observer<Boolean> { isSigned ->
        if (!isSigned) {
            Intent(requireContext(), MainActivity::class.java).apply {
                startActivity(this)
                requireActivity().finish()
            }
        }
    }

    private val observerCategories = Observer<List<String>?> { categories ->
        if (categories != null) {
            this.categories.addAll(categories)
            if ((requireActivity() as HomeActivity).checkForInternet(requireContext())) {
                viewModel.getBooksByTerms(categories)
            } else {
                goToNoInternetActivity()
            }
        } else {
            Intent(requireContext(), CategoryActivity::class.java).apply {
                startActivity(this)
                requireActivity().finish()
            }
        }
    }

    private val observerBooks = Observer<HashMap<String, List<Book>>> { hashMap ->
        if ((requireActivity() as HomeActivity).checkForInternet(requireContext())) {
            val key = hashMap.keys.map { it }[0]
            val finalKey = categories.filter { it == key }[0]

            if (finalKey == categories[0]) {
                adapterFirstCategory.update(hashMap[finalKey]?.map { it })
                binding.bookFirstCategoryTextView.text = categories[0]
            } else if (finalKey == categories[1]) {
                adapterSecondCategory.update(hashMap[finalKey]?.map { it })
                binding.bookSecondCategoryTextView.text = categories[1]
            } else if (finalKey == categories[2]) {
                adapterThirdCategory.update(hashMap[finalKey]?.map { it })
                binding.bookThirdCategoryTextView.text = categories[2]
            }

            booksListed++

            if (booksListed == 3) {
                binding.booksLoadingAnimation.visibility = View.GONE
                binding.booksLoadingAnimation.cancelAnimation()
                binding.container.visibility = View.VISIBLE
                binding.infoContainer.visibility = View.VISIBLE
            }
        } else {
            goToNoInternetActivity()
        }
    }

    private val observerUserName = Observer<String> {
        binding.greetingsTextView.text = "Olá, $it"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookListingFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(BookListingViewModel::class.java)

        setupRecyclerView()
        setupObservers()
        setupButtons()

        if ((requireActivity() as HomeActivity).checkForInternet(requireContext())) {
            viewModel.getCurrentUserName()
        } else {
            goToNoInternetActivity()
        }

        if ((requireActivity() as HomeActivity).checkForInternet(requireContext())) {
            viewModel.getCurrentUser()
        } else {
            goToNoInternetActivity()
        }
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
        viewModel.user.observe(viewLifecycleOwner, observerUser)
        viewModel.categories.observe(viewLifecycleOwner, observerCategories)
        viewModel.books.observe(viewLifecycleOwner, observerBooks)
        viewModel.isSignedIn.observe(viewLifecycleOwner, observerSignOut)
        viewModel.userName.observe(viewLifecycleOwner, observerUserName)
    }
}