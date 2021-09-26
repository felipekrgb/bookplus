package com.example.book.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.book.R
import com.example.book.databinding.IntroductionFragmentBinding
import com.example.book.utils.replaceFragment
import com.example.book.view.activities.MainActivity

class IntroductionFragment : Fragment(R.layout.introduction_fragment) {

    private lateinit var binding: IntroductionFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = IntroductionFragmentBinding.bind(view)

        binding.singInButton.setOnClickListener {
            (requireActivity() as MainActivity).replaceFragment(SignInFragment())
        }

        binding.signUpButton.setOnClickListener {
            (requireActivity() as MainActivity).replaceFragment(SignUpFragment.newInstance())
        }

    }
}