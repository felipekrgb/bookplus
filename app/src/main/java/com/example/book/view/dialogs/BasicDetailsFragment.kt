package com.example.book.view.dialogs

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.book.R
import com.example.book.databinding.BasicDetailsFragmentBinding
import com.example.book.model.Book
import com.example.book.view.activities.BookDetailsActivity
import com.example.book.viewmodel.BasicDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasicDetailsFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(book: Book): BasicDetailsFragment {
            return BasicDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("book", book)
                }
            }
        }
    }

    private lateinit var binding: BasicDetailsFragmentBinding
    private lateinit var viewModel: BasicDetailsViewModel
    private lateinit var book: Book

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.basic_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BasicDetailsFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(BasicDetailsViewModel::class.java)

        book = arguments?.getSerializable("book") as Book

        book.volumeInfo.imageLinks?.thumbnail.let {
            Glide.with(view.context).load(it).into(binding.bookImageView)
        }

        setupDetailsButton()

    }

    override fun getTheme() = R.style.CustomBottomSheetDialog

    private fun setupDetailsButton() {

        binding.bookDetailsButton.setOnClickListener {

            val intentToDetails =
                Intent(activity?.applicationContext, BookDetailsActivity::class.java)
            intentToDetails.putExtra("book", book)
            startActivity(intentToDetails)

        }

    }

}