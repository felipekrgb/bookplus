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
import com.example.book.utils.hideKeyboard
import com.example.book.utils.replaceFragment
import com.example.book.utils.snackBar
import com.example.book.view.activities.HomeActivity
import com.example.book.view.activities.MainActivity
import com.example.book.viewmodel.SignInViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.sign_in_fragment) {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: SignInFragmentBinding

    private val observerUser = Observer<FirebaseUser?> {
        Intent(requireContext(), HomeActivity::class.java).apply {
            startActivity(this)
            requireActivity().finish()
        }
    }

    private val observerError = Observer<String> {
        binding.buttonLogin.apply {
            isEnabled = true
            alpha = 1f
        }

        binding.buttonLoginTextView.visibility = View.VISIBLE
        binding.buttonLoginProgressBar.visibility = View.INVISIBLE

        binding.arrowBackImageView.apply {
            isClickable = true
            alpha = 1f
        }

        binding.registerTextView.apply {
            isClickable = true
            alpha = 1f
        }

        if (it == "There is no user record corresponding to this identifier. The user may have been deleted.") {
            showSnackbar(R.string.error_login_no_user, R.color.red)
        } else if (it == "The password is invalid or the user does not have a password.") {
            showSnackbar(R.string.user_invalided, R.color.red)
        } else {
            showSnackbar(R.string.error_generic_login, R.color.red)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding = SignInFragmentBinding.bind(view)

        setupObservers()
        setupSettingsSignIn()
        setupBackButton()
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner, observerUser)
        viewModel.error.observe(viewLifecycleOwner, observerError)
    }

    private fun setupSettingsSignIn() {
        binding.buttonLogin.setOnClickListener {
            val inputEmail = binding.userEmailEditText
            val inputPassword = binding.editTextPassword.editText

            binding.buttonLogin.apply {
                isEnabled = false
                alpha = 0.5f
            }

            binding.buttonLoginTextView.visibility = View.GONE
            binding.buttonLoginProgressBar.visibility = View.VISIBLE

            binding.arrowBackImageView.apply {
                isClickable = false
                alpha = 0.5f
            }

            binding.registerTextView.apply {
                isClickable = false
                alpha = 0.5f
            }

            (requireActivity() as AppCompatActivity).hideKeyboard()

            if (!inputEmail?.text.isNullOrEmpty() && !inputPassword?.text.isNullOrEmpty()) {
                viewModel.signInEmailAndPassword(
                    email = inputEmail?.text.toString(),
                    password = inputPassword?.text.toString()
                )
            } else {
                binding.buttonLogin.apply {
                    isEnabled = true
                    alpha = 1f
                }

                binding.buttonLoginTextView.visibility = View.VISIBLE
                binding.buttonLoginProgressBar.visibility = View.INVISIBLE

                binding.arrowBackImageView.apply {
                    isClickable = true
                    alpha = 1f
                }

                binding.registerTextView.apply {
                    isClickable = true
                    alpha = 1f
                }

                showSnackbar(R.string.no_user, R.color.red)
            }
        }

        binding.registerTextView.setOnClickListener {
            (requireActivity() as AppCompatActivity).replaceFragment(SignUpFragment.newInstance())
        }
    }

    private fun setupBackButton() {
        binding.arrowBackImageView.setOnClickListener {
            (requireActivity() as AppCompatActivity).replaceFragment(IntroductionFragment())
        }
    }

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val activity = requireActivity() as MainActivity
        val view = binding.registerNotTextView
        activity.snackBar(view, msgId, colorId)
    }
}