package com.example.book.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.book.R
import com.example.book.databinding.FragmentSignUpBinding
import com.example.book.utils.hideKeyboard
import com.example.book.utils.replaceFragment
import com.example.book.utils.snackBar
import com.example.book.view.activities.HomeActivity
import com.example.book.view.activities.MainActivity
import com.example.book.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.sign_up_fragment) {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding

    private val observerNewUser = Observer<FirebaseUser?> {
        Intent(requireContext(), HomeActivity::class.java).apply {
            startActivity(this)
            requireActivity().finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding = FragmentSignUpBinding.bind(view)

        setupObservers()
        setupSettingsSignUp()
        setupBackButton()
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner, observerNewUser)
    }

    private fun setupSettingsSignUp() {
        binding.buttonCreate.setOnClickListener {
            val inputEmail = binding.editTextEmail.editText
            val inputPassword = binding.editTextPassword.editText
            val inputUser = binding.editTextUser.editText

            (requireActivity() as AppCompatActivity).hideKeyboard()

            if (!inputEmail?.text.isNullOrEmpty() && !inputPassword?.text.isNullOrEmpty() && !inputUser?.text.isNullOrEmpty()) {
                viewModel.signUpEmailAndPassword(
                    email = inputEmail?.text.toString(),
                    password = inputPassword?.text.toString(),
                    user = inputUser?.text.toString()
                )
            } else {
                showSnackbar(R.string.no_user, R.color.red)
            }
        }

        binding.loginTextView.setOnClickListener {
            (requireActivity() as AppCompatActivity).replaceFragment(SignInFragment.newInstance())

        }
    }

    private fun setupBackButton() {
        binding.arrowBackImageView.setOnClickListener {
            (requireActivity() as AppCompatActivity).replaceFragment(IntroductionFragment())
        }
    }

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val activity = requireActivity() as MainActivity
        val bottomNav = activity.binding.container
        activity.snackBar(bottomNav, msgId, colorId)
    }
}