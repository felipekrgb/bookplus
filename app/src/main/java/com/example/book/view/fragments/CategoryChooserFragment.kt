package com.example.book.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.book.R
import com.example.book.databinding.CategoryChooserFragmentBinding
import com.example.book.model.UserCategories
import com.example.book.utils.snackBar
import com.example.book.view.activities.HomeActivity
import com.example.book.viewmodel.CategoryChooserViewModel
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryChooserFragment : Fragment(R.layout.category_chooser_fragment) {

    companion object {
        fun newInstance() = CategoryChooserFragment()
    }

    private lateinit var viewModel: CategoryChooserViewModel
    private lateinit var binding: CategoryChooserFragmentBinding
    private val listOfCategories = mutableListOf<String>()

    private val observerUser = Observer<FirebaseUser> { user ->
        setupSaveCategoriesButton(user.uid)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = CategoryChooserFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(CategoryChooserViewModel::class.java)

        viewModel.user.observe(viewLifecycleOwner, observerUser)

        viewModel.getCurrentUser()
        setupChips()
    }

    private fun setupChips() {
        binding.chipBiology.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.BIOLOGY)
        }

        binding.chipCulinary.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.CULINARY)
        }

        binding.chipSport.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.SPORT)
        }

        binding.chipGeography.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.GEOGRAPHY)
        }

        binding.chipPolitics.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.POLITICS)
        }

        binding.chipEducation.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.EDUCATION)
        }

        binding.chipAction.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.ACTION)
        }

        binding.chipAdventure.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.ADVENTURE)
        }

        binding.chipHorror.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.HORROR)
        }

        binding.chipBiography.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.BIOGRAPHY)
        }

        binding.chipSelfHelp.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.SELF_HELP)
        }

        binding.chipPsychology.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.PSYCHOLOGY)
        }

        binding.chipTechnology.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.TECHNOLOGY)
        }

        binding.chipDrama.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.DRAMA)
        }

        binding.chipRomance.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.ROMANCE)
        }

        binding.chipChildren.setOnCheckedChangeListener { group, checkedId ->
            handleChipChecked(group as Chip, checkedId, Category.CHILDREN)
        }
    }

    private fun setupSaveCategoriesButton(userId: String) {
        binding.saveCategoriesButton.setOnClickListener {
            if (listOfCategories.size != 3) {
                (requireActivity() as AppCompatActivity).snackBar(
                    it,
                    R.string.categories_error,
                    R.color.red,
                )
            } else {
                viewModel.addUserCategories(
                    UserCategories(userId = userId, categories = listOfCategories)
                )

                Intent(requireContext(), HomeActivity::class.java).apply {
                    startActivity(this)
                    requireActivity().finish()
                }
            }
        }
    }

    private fun handleChipChecked(chip: Chip, toCheck: Boolean, category: Category) {
        if (listOfCategories.size == 3 && toCheck) {
            chip.isChecked = false
        } else if (toCheck) {
            listOfCategories.add(category.categoryName)
        } else {
            listOfCategories.remove(category.categoryName)
        }
        println(listOfCategories)
    }

    enum class Category(val categoryName: String) {
        BIOLOGY("Biologia"),
        CULINARY("Culinária"),
        SPORT("Esporte"),
        GEOGRAPHY("Geografia"),
        POLITICS("Política"),
        EDUCATION("Educação"),
        ACTION("Ação"),
        ADVENTURE("Aventura"),
        HORROR("Terror"),
        BIOGRAPHY("Biografia"),
        SELF_HELP("Autoajuda"),
        PSYCHOLOGY("Psicologia"),
        TECHNOLOGY("Tecnologia"),
        DRAMA("Drama"),
        ROMANCE("Romance"),
        CHILDREN("Infantil"),
    }

}