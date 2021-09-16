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
import com.example.book.databinding.SignInFragmentBinding
import com.example.book.utils.replaceFragment
import com.example.book.utils.snackBar
import com.example.book.view.activities.HomeActivity
import com.example.book.view.activities.MainActivity
import com.example.book.viewmodel.AuthenticationViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.sign_in_fragment) {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var binding: SignInFragmentBinding

    private val observerUser = Observer<FirebaseUser?> {
        Intent(requireContext(), HomeActivity::class.java).apply {
            startActivity(this)
        }
    }
    private val observerError = Observer<String> {
        Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        binding = SignInFragmentBinding.bind(view)

        setupObservers()
        setupSettingsSignIn()

    }

    private fun setupObservers() {

        viewModel.user.observe(viewLifecycleOwner, observerUser)
        viewModel.error.observe(viewLifecycleOwner, observerError)
    }

    private fun setupSettingsSignIn() {

        binding.buttonLogin.setOnClickListener {
            val inputEmail = binding.editTextUser.editText
            val inputPassword = binding.editTextPassword.editText

            if (!inputEmail?.text.isNullOrEmpty() && !inputPassword?.text.isNullOrEmpty()) {
                viewModel.signInEmailAndPassword(
                    email = inputEmail?.text.toString(),
                    password = inputPassword?.text.toString()
                )
            } else {
                showSnackbar(R.string.no_user, R.color.red)
            }
        }
        binding.registerTextView.setOnClickListener {
            (requireActivity() as AppCompatActivity).replaceFragment(SignUpFragment.newInstance())
        }
    }

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val activity = requireActivity() as MainActivity
        val bottomNav = activity.binding.container
        activity.snackBar(bottomNav, msgId, colorId)
    }
}