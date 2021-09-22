package com.example.book.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.book.R
import com.example.book.databinding.PdfBooksFragmentBinding

class PdfBooksFragment : Fragment(R.layout.pdf_books_fragment) {

    companion object {
        fun newInstance() = PdfBooksFragment()
    }

    private lateinit var viewModel: PdfBooksViewModel
    private lateinit var binding: PdfBooksFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PdfBooksViewModel::class.java)
        binding = PdfBooksFragmentBinding.bind(view)
    }

}