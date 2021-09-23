package com.example.book.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.book.R
import com.example.book.databinding.NoInternetFragmentBinding
import com.example.book.view.activities.HomeActivity

class NoInternetFragment : Fragment(R.layout.no_internet_fragment) {

    private lateinit var binding: NoInternetFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NoInternetFragmentBinding.bind(view)


        binding.buttonRefesh.setOnClickListener {
            Intent(context, HomeActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}