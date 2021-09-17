package com.example.book.view.fragments

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.text.HtmlCompat
import androidx.core.content.res.ResourcesCompat
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
import com.example.book.view.activities.BookDetailsActivity
import com.example.book.viewmodel.BookDetailsViewModel
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

    private lateinit var viewModel: BookDetailsViewModel
    private lateinit var binding: BookDetailsFragmentBinding
    private var colorPallete: Int? = null
    private val observerBook = Observer<Book?> {
        bindData(it)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookDetailsFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(BookDetailsViewModel::class.java)
        viewModel.book.observe(viewLifecycleOwner, observerBook)

        val bookId = arguments?.getString("book_id") as String

        viewModel.getBookById(bookId)

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
                                (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(colorPallete!!))

                                var background = ResourcesCompat.getDrawable(resources, R.drawable.gradient, null) as GradientDrawable
                                var colorList = intArrayOf(
                                    colorPallete!!,
                                    colorPallete!!,
                                )

                                background.colors = colorList
                                binding.colorBandLayout.background = background

                            }
                            return true
                        }

                    })
                    .into(binding.bookImageView)
            }
        }

        if (colorPallete == null) {
            binding.bookTitleTextView.setBackgroundColor(getColor(
                requireActivity(),
                R.color.brown_medium
            ))
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
        binding.bookDescriptionTextView.text = if (book.volumeInfo.description != null) HtmlCompat.fromHtml(
            book.volumeInfo.description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
            .toString() else "Nenhuma sinopse disponível."

        binding.backButton.setOnClickListener{
            (requireActivity() as AppCompatActivity).finish()
        }

    }

}