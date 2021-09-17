package com.example.book.view.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.book.R
import com.example.book.databinding.BasicDetailsFragmentBinding
import com.example.book.model.Book
import com.example.book.view.activities.BookDetailsActivity
import com.example.book.viewmodel.BasicDetailsViewModel
import com.example.book.viewmodel.BookFavoritesViewModel
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
    private lateinit var book: Book
    private lateinit var viewModelFireBase: BookFavoritesViewModel

    private val observerBookFav = Observer<List<String>> { listOfFavorites ->
        binding.checkBoxSave.isChecked = listOfFavorites.contains(book.id)
    }


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

        viewModelFireBase = ViewModelProvider(this).get(BookFavoritesViewModel::class.java)


        book = arguments?.getSerializable("book") as Book

        book.volumeInfo.imageLinks?.thumbnail.let {
            Glide.with(view.context).load(it).placeholder(R.drawable.no_cover_thumb)
                .into(binding.bookImageView)
        }

        book.volumeInfo.title.let {
            binding.bookTitleTextView.text = it ?: "Sem título"
        }



        binding.checkBoxSave.setOnCheckedChangeListener { checked, isChecked ->
            if (isChecked) {
                viewModelFireBase.save(book.id)
            } else {
                viewModelFireBase.delete(book.id)
            }
        }

        setupDetailsButton()
        viewModelFireBase.booksFavs.observe(viewLifecycleOwner, observerBookFav)
        viewModelFireBase.fetchAllBooksFav()
        setupImageButton()

    }

    override fun getTheme() = R.style.CustomBottomSheetDialog

    private fun setupDetailsButton() {
        binding.bookDetailsButton.setOnClickListener {
            val intentToDetails =
                Intent(activity?.applicationContext, BookDetailsActivity::class.java)
            intentToDetails.putExtra("book", book)
            dismiss()
            startActivity(intentToDetails)
        }
    }

    private fun setupImageButton() {
        binding.bookImageView.setOnClickListener {
            val intentToDetails =
                Intent(activity?.applicationContext, BookDetailsActivity::class.java)
            intentToDetails.putExtra("book", book)
            dismiss()
            startActivity(intentToDetails)
        }
    }
}