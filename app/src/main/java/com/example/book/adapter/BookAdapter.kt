package com.example.book.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.book.R
import com.example.book.databinding.BookItemFragmentBinding
import com.example.book.model.Book

class BookAdapter : RecyclerView.Adapter<BookViewHolder>() {

    var bookList = mutableListOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item_fragment, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        bookList[position].apply {
            holder.bind(this)
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

    val binding = BookItemFragmentBinding.bind(view)

    fun bind (book: Book) {
        
        book.imageLinks.let {
            Glide.with(itemView.context)
                .load(it?.thumbnail)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.bookImageView)
        }

    }

}