package com.example.book.view.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.book.R
import com.example.book.databinding.NoInternetFragmentBinding
import com.example.book.utils.checkForInternet
import com.example.book.utils.snackBar
import com.example.book.view.activities.NoInternetActivity

class NoInternetFragment : Fragment(R.layout.no_internet_fragment) {

    private lateinit var binding: NoInternetFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NoInternetFragmentBinding.bind(view)

        binding.bookSearchAnimation.visibility = View.VISIBLE
        binding.bookSearchAnimation.playAnimation()

        binding.textViewNoInternet.setOnClickListener {
            if ((requireActivity() as AppCompatActivity).checkForInternet(requireContext())) {
                requireActivity().finish()
            } else {
                showSnackbar(R.string.no_conection, R.color.red)
            }
        }
    }

    private fun showSnackbar(@StringRes msgId: Int, @ColorRes colorId: Int) {
        val activity = requireActivity() as NoInternetActivity
        val view = binding.textViewNoInternet
        activity.snackBar(view, msgId, colorId)
    }

}