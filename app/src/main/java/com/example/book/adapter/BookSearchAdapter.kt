package com.example.book.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.example.book.R
import com.example.book.databinding.BookItemSearchBinding
import com.example.book.model.Book

class BookSearchAdapter(private val onClick: (Book) -> Unit) :
    RecyclerView.Adapter<BookSearchViewHolder>() {

    private var listOfBooks = mutableListOf<Book>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item_search, parent, false).let {
                BookSearchViewHolder(it)
            }
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        listOfBooks[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int = listOfBooks.size

    fun update(newList: List<Book>, clearList: Boolean = false) {
        if (clearList) {
            listOfBooks.clear()
        }
        listOfBooks.addAll(newList)
        notifyDataSetChanged()
    }
}

class BookSearchViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    private val binding = BookItemSearchBinding.bind(item)

    fun bind(book: Book) {

        binding.titleSearch.text = book.volumeInfo.title

        book.volumeInfo.authors?.let {
            binding.authorSearch.text = it[0]
        }

        book.volumeInfo.imageLinks.let {
            Glide.with(itemView.context)
                .asBitmap()
                .load(it?.thumbnail)
                .placeholder(R.drawable.no_cover_thumb)
                .into(object: BitmapImageViewTarget(binding.imageViewBookSearch) {

                    override fun onResourceReady(
                        bitmap: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        super.onResourceReady(bitmap, transition)
                        Palette.generateAsync(bitmap) {

                            binding.bookCoverCard.setStrokeColor(
                                it!!.getMutedColor(
                                    itemView.context.getColor(
                                        R.color.brown_light
                                    )
                                )
                            )

                            binding.colorCard.apply {
                                background.setTint(
                                    it!!.getMutedColor(
                                        itemView.context.getColor(
                                            R.color.brown_medium
                                        )
                                    )
                                )
                            }

                        }
                    }
                })
        }

    }
}