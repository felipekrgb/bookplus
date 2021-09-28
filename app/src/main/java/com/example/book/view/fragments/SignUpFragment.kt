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
import com.example.book.databinding.SignUpFragmentBinding
import com.example.book.utils.checkForInternet
import com.example.book.utils.hideKeyboard
import com.example.book.utils.replaceFragment
import com.example.book.utils.snackBar
import com.example.book.view.activities.CategoryActivity
import com.example.book.view.activities.MainActivity
import com.example.book.view.activities.NoInternetActivity
import com.example.book.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.sign_up_fragment) {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: SignUpFragmentBinding

    private val observerNewUser = Observer<FirebaseUser?> {
        binding.buttonCreate.apply {
            isEnabled = true
            alpha = 1f
        }

        binding.buttonCreateTextView.visibility = View.VISIBLE
        binding.buttonCreateProgressBar.visibility = View.INVISIBLE

        binding.arrowBackImageView.apply {
            isClickable = true
            alpha = 1f
        }

        binding.loginTextView.apply {
            isClickable = true
            alpha = 1f
        }

        Intent(requireContext(), CategoryActivity::class.java).apply {
            startActivity(this)
            requireActivity().finish()
        }

    }

    private val observerError = Observer<String> {
        binding.buttonCreate.apply {
            isEnabled = true
            alpha = 1f
        }
        binding.buttonCreate.visibility = View.VISIBLE
        binding.buttonCreateTextView.visibility = View.VISIBLE
        binding.buttonCreateProgressBar.visibility = View.INVISIBLE

        binding.arrowBackImageView.apply {
            isClickable = true
            alpha = 1f
        }

        binding.loginTextView.apply {
            isClickable = true
            alpha = 1f
        }

        if (it == "The email address is already in use by another account.") {
            showSnackbar(R.string.error_created_account_user_existent, R.color.red)
        } else if (it == "The email address is badly formatted.") {
            showSnackbar(R.string.error_email_format, R.color.red)
        } else {
            if (it == "A network error (such as timeout, interrupted connection or unreachable host) has occurred." &&
                !(requireActivity() as AppCompatActivity).checkForInternet(requireContext())
            ) {
                Intent(requireActivity(), NoInternetActivity::class.java).apply {
                    startActivity(this)
                }
            } else {
                showSnackbar(R.string.error_generic_created, R.color.red)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding = SignUpFragmentBinding.bind(view)

        setupObservers()
        setupSettingsSignUp()
        setupBackButton()
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner, observerNewUser)
        viewModel.error.observe(viewLifecycleOwner, observerError)
    }

    private fun setupSettingsSignUp() {
        binding.buttonCreate.setOnClickListener {
            val inputUser = binding.editTextUser.editText
            val inputEmail = binding.editTextEmail.editText
            val inputPassword = binding.editTextPassword.editText

            binding.buttonCreate.apply {
                isEnabled = false
                alpha = 0.5f
            }
            binding.buttonCreateTextView.visibility = View.GONE
            binding.buttonCreateProgressBar.visibility = View.VISIBLE

            binding.arrowBackImageView.apply {
                isClickable = false
                alpha = 0.5f
            }

            binding.loginTextView.apply {
                isClickable = false
                alpha = 0.5f
            }

            (requireActivity() as AppCompatActivity).hideKeyboard()

            if (!inputEmail?.text.isNullOrEmpty() && !inputPassword?.text.isNullOrEmpty() && !inputUser?.text.isNullOrEmpty()) {
                if (inputPassword!!.length() >= 6) {
                    if ((requireActivity() as AppCompatActivity).checkForInternet(requireContext())) {
                        viewModel.signUpEmailAndPassword(
                            email = inputEmail?.text.toString(),
                            password = inputPassword?.text.toString(),
                            name = inputUser?.text.toString()
                        )
                    } else {
                        binding.buttonCreate.apply {
                            isEnabled = true
                            alpha = 1f
                        }
                        binding.buttonCreateTextView.visibility = View.VISIBLE
                        binding.buttonCreateProgressBar.visibility = View.INVISIBLE

                        binding.arrowBackImageView.apply {
                            isClickable = true
                            alpha = 1f
                        }

                        binding.loginTextView.apply {
                            isClickable = true
                            alpha = 1f
                        }

                        Intent(requireActivity(), NoInternetActivity::class.java).apply {
                            startActivity(this)
                        }
                    }
                } else {

                    binding.buttonCreate.apply {
                        isEnabled = true
                        alpha = 1f
                    }
                    binding.buttonCreateTextView.visibility = View.VISIBLE
                    binding.buttonCreateProgressBar.visibility = View.INVISIBLE

                    binding.arrowBackImageView.apply {
                        isClickable = true
                        alpha = 1f
                    }

                    binding.loginTextView.apply {
                        isClickable = true
                        alpha = 1f
                    }

                    showSnackbar(R.string.error_created_account_password_invalided, R.color.red)

                }
            } else {
                binding.buttonCreate.apply {
                    isEnabled = true
                    alpha = 1f
                }

                binding.buttonCreateTextView.visibility = View.VISIBLE
                binding.buttonCreateProgressBar.visibility = View.INVISIBLE

                binding.arrowBackImageView.apply {
                    isClickable = true
                    alpha = 1f
                }

                binding.loginTextView.apply {
                    isClickable = true
                    alpha = 1f
                }

                showSnackbar(R.string.error_created_account_empty, R.color.red)
            }
        }

        binding.loginTextView.setOnClickListener {
            (requireActivity() as AppCompatActivity).replaceFragment(SignInFragment())
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