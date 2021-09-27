package com.example.book.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.book.R
import com.example.book.databinding.ProfileEditFragmentBinding
import com.example.book.model.Category
import com.example.book.model.UserCategories
import com.example.book.utils.checkForInternet
import com.example.book.utils.snackBar
import com.example.book.view.activities.HomeActivity
import com.example.book.view.activities.NoInternetActivity
import com.example.book.viewmodel.ProfileEditViewModel
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditFragment : Fragment(R.layout.profile_edit_fragment) {

    companion object {
        fun newInstance() = ProfileEditFragment()
    }

    private lateinit var viewModel: ProfileEditViewModel
    private lateinit var binding: ProfileEditFragmentBinding
    private lateinit var user: FirebaseUser
    private val userCategories = mutableListOf<String>()
    private var userCategoriesId: Long = 0L

    private val observerUser = Observer<FirebaseUser> { user ->
        this.user = user
        viewModel.getUserCategories(user.uid)
    }

    private val observerUserName = Observer<String> { userName ->
        binding.userNameEditText.editText?.setText(userName)
    }

    private val observerUserCategories = Observer<UserCategories> { userCategories ->
        this.userCategories.addAll(userCategories.categories)
        this.userCategoriesId = userCategories.id
        setupChips()
        setupEditProfileButton(user.uid)
    }

    override fun onResume() {
        if ((requireActivity() as HomeActivity).checkForInternet(requireContext())) {
            viewModel.getCurrentUserName()
            viewModel.getCurrentUser()
        } else {
            Intent(requireActivity(), NoInternetActivity::class.java).apply {
                startActivity(this)
            }
        }
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileEditViewModel::class.java)
        binding = ProfileEditFragmentBinding.bind(view)

        viewModel.user.observe(viewLifecycleOwner, observerUser)
        viewModel.userName.observe(viewLifecycleOwner, observerUserName)
        viewModel.userCategories.observe(viewLifecycleOwner, observerUserCategories)
    }

    private fun checkIfCategoryMatch(chipCategory: Category): Boolean {
        userCategories.forEach { category ->
            if (chipCategory.categoryName == category) {
                return true
            }
        }
        return false
    }

    private fun setupChips() {
        binding.chipBiology.apply {
            isChecked = checkIfCategoryMatch(Category.BIOLOGY)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.BIOLOGY)
            }
        }

        binding.chipCulinary.apply {
            isChecked = checkIfCategoryMatch(Category.CULINARY)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.CULINARY)
            }
        }

        binding.chipSport.apply {
            isChecked = checkIfCategoryMatch(Category.SPORT)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.SPORT)
            }
        }

        binding.chipGeography.apply {
            isChecked = checkIfCategoryMatch(Category.GEOGRAPHY)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.GEOGRAPHY)
            }
        }


        binding.chipPolitics.apply {
            isChecked = checkIfCategoryMatch(Category.POLITICS)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.POLITICS)
            }
        }


        binding.chipEducation.apply {
            isChecked = checkIfCategoryMatch(Category.EDUCATION)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.EDUCATION)
            }
        }


        binding.chipAction.apply {
            isChecked = checkIfCategoryMatch(Category.ACTION)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.ACTION)
            }
        }

        binding.chipAdventure.apply {
            isChecked = checkIfCategoryMatch(Category.ADVENTURE)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.ADVENTURE)
            }
        }

        binding.chipHorror.apply {
            isChecked = checkIfCategoryMatch(Category.HORROR)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.HORROR)
            }
        }

        binding.chipBiography.apply {
            isChecked = checkIfCategoryMatch(Category.BIOGRAPHY)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.BIOGRAPHY)
            }
        }

        binding.chipSelfHelp.apply {
            isChecked = checkIfCategoryMatch(Category.SELF_HELP)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.SELF_HELP)
            }
        }

        binding.chipPsychology.apply {
            isChecked = checkIfCategoryMatch(Category.PSYCHOLOGY)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.PSYCHOLOGY)
            }
        }

        binding.chipTechnology.apply {
            isChecked = checkIfCategoryMatch(Category.TECHNOLOGY)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.TECHNOLOGY)
            }
        }

        binding.chipDrama.apply {
            isChecked = checkIfCategoryMatch(Category.DRAMA)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.DRAMA)
            }
        }

        binding.chipRomance.apply {
            isChecked = checkIfCategoryMatch(Category.ROMANCE)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.ROMANCE)
            }
        }

        binding.chipChildren.apply {
            isChecked = checkIfCategoryMatch(Category.CHILDREN)
            setOnCheckedChangeListener { group, checkedId ->
                handleChipChecked(group as Chip, checkedId, Category.CHILDREN)
            }
        }
    }

    private fun setupEditProfileButton(userId: String) {
        binding.editProfileButton.setOnClickListener {
            if (binding.userNameEditText?.editText?.text.isNullOrEmpty()) {
                (requireActivity() as AppCompatActivity).snackBar(
                    it,
                    R.string.error_name_field_empty,
                    R.color.red,
                )
            } else if (userCategories.size != 3) {
                (requireActivity() as AppCompatActivity).snackBar(
                    it,
                    R.string.categories_error,
                    R.color.red,
                )
            } else {
                viewModel.updateName(binding.userNameEditText.editText?.text.toString())
                viewModel.updateCategories(
                    UserCategories(
                        id = userCategoriesId,
                        userId = userId,
                        categories = userCategories
                    )
                )

                Intent(requireContext(), HomeActivity::class.java).apply {
                    startActivity(this)
                    requireActivity().finish()
                }
            }
        }
    }

    private fun handleChipChecked(
        chip: Chip,
        toCheck: Boolean,
        category: Category
    ) {
        if (userCategories.size == 3 && toCheck) {
            chip.isChecked = false
        } else if (toCheck) {
            userCategories.add(category.categoryName)
        } else {
            userCategories.remove(category.categoryName)
        }
    }


}