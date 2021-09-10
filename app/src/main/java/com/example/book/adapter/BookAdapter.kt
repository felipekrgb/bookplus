package com.example.book.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.book.R
import com.example.book.databinding.ItemBookBinding
import com.example.book.model.Book

class BookAdapter(val onClick: (Book) -> Unit) : RecyclerView.Adapter<BookViewHolder>() {

    var bookList = mutableListOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        bookList[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int = bookList.size

    fun update(newList: List<Book>) {
        bookList = mutableListOf()
        bookList.addAll(newList)
        notifyDataSetChanged()
    }

}

class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemBookBinding.bind(view)

    fun bind(book: Book) {
        book.volumeInfo.imageLinks.let {
            Glide.with(itemView.context)
                .load(it?.thumbnail)
                .placeholder(R.drawable.no_cover_thumb)
                .into(binding.bookImageView)
        }

    }

}