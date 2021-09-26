package com.example.book.view.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.book.R
import com.example.book.databinding.BookDetailsFragmentBinding
import com.example.book.model.Book
import com.example.book.viewmodel.BookDetailsViewModel
import com.example.book.viewmodel.BookFavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailsFragment : Fragment(R.layout.book_details_fragment) {

    companion object {
        fun newInstance(id: String): BookDetailsFragment {
            return BookDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString("book_id", id)
                }
            }
        }
    }

    private lateinit var viewModelFireBase: BookFavoritesViewModel
    private lateinit var bookFavs: String
    private lateinit var viewModel: BookDetailsViewModel
    private lateinit var binding: BookDetailsFragmentBinding
    private var colorPallete: Int? = null
    private val observerBook = Observer<Book?> {
        bindData(it)
    }
    private val observerBookFav = Observer<List<String>> { listOfFavorites ->
        binding.checkBoxSave.isChecked = listOfFavorites.contains(bookFavs)
    }
    private val observerLoading = Observer<Boolean> { isLoading ->
        if (isLoading) {
            binding.bookSearchAnimation.visibility = View.VISIBLE
            binding.scrollViewLayout.visibility = View.INVISIBLE
            binding.bookSearchAnimation.playAnimation()
        } else {
            binding.bookSearchAnimation.cancelAnimation()
            binding.scrollViewLayout.visibility = View.VISIBLE
            binding.bookSearchAnimation.visibility = View.INVISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookDetailsFragmentBinding.bind(view)

        viewModelFireBase = ViewModelProvider(this).get(BookFavoritesViewModel::class.java)
        viewModel = ViewModelProvider(this).get(BookDetailsViewModel::class.java)

        setupObservers()
        setupViewModelFuns()
        setupCheckIcon()

        val bookId = arguments?.getString("book_id") as String
        bookFavs = bookId
        viewModel.getBookById(bookId)
    }

    private fun setupCheckIcon() {
        binding.checkBoxSave.setOnCheckedChangeListener { checked, isChecked ->
            if (isChecked) {
                viewModelFireBase.save(bookFavs)
            } else {
                viewModelFireBase.delete(bookFavs)
            }
        }
    }

    private fun setupViewModelFuns() {
        viewModelFireBase.fetchAllBooksFav()
    }

    private fun setupObservers() {
        viewModel.book.observe(viewLifecycleOwner, observerBook)
        viewModel.isLoading.observe(viewLifecycleOwner, observerLoading)
        viewModelFireBase.booksFavs.observe(viewLifecycleOwner, observerBookFav)
    }

    private fun bindData(book: Book?) {
        book!!.volumeInfo.imageLinks?.thumbnail?.apply {
            context?.let {
                colorPallete = R.color.brown_light
                Glide.with(it).asBitmap()
                    .load(this).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.no_cover_thumb)
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            requireActivity().startPostponedEnterTransition()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            requireActivity().startPostponedEnterTransition()
                            if (resource != null) {
                                val p: Palette = Palette.from(resource).generate()

                                colorPallete = p.getMutedColor(
                                    getColor(
                                        requireActivity(),
                                        R.color.brown_light
                                    )
                                )

                                binding.bookImageView.setImageBitmap(resource)

                                val background = ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.background_detail,
                                    null
                                )

                                background?.setTint(colorPallete!!)
                                binding.colorBandLayout.background = background
                            }
                            return true
                        }

                    })
                    .into(binding.bookImageView)
            }
        }

        if (colorPallete == null) {
            binding.bookTitleTextView.setBackgroundColor(
                getColor(
                    requireActivity(),
                    R.color.brown_medium
                )
            )
            binding.bookTitleTextView.setBackgroundColor(
                getColor(
                    requireActivity(),
                    R.color.brown_light
                )
            )
        }

        binding.bookTitleTextView.text = book.volumeInfo.title
        binding.bookAuthorTextView.text = book.volumeInfo.authors?.get(0) ?: "Autor indisponível"
        binding.pageCountTextView.text = book.volumeInfo.pageCount.toString()
        binding.releaseDateTextView.text = book.volumeInfo.publishedDate
        binding.bookDescriptionTextView.text =
            if (book.volumeInfo.description != null) HtmlCompat.fromHtml(
                book.volumeInfo.description,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
                .toString() else "Nenhuma sinopse disponível."

        binding.backButton.setOnClickListener {
            (requireActivity() as AppCompatActivity).finish()
        }

        binding.buyActionButton.apply {

            if (book.saleInfo?.buyLink != null) {

                setOnClickListener {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(book.saleInfo!!.buyLink))
                    startActivity(browserIntent)
                }

            } else if (book.volumeInfo.previewLink != null) {

                this.text = getText(R.string.preview)
                setOnClickListener {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(book.volumeInfo.previewLink))
                    startActivity(browserIntent)
                }

            } else {
                this.isEnabled = false
                this.alpha = 0.5f
            }

        }

    }

}