package com.example.book.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.book.R
import com.example.book.databinding.ItemFavBinding
import com.example.book.model.Book

class BookFavoritesAdapter(val onClick: (Book) -> Unit) : RecyclerView.Adapter<BooksViewHolder>() {

    private var listOfBooks = mutableListOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_fav, parent, false).let {
            return BooksViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        listOfBooks[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int = listOfBooks.size

    fun refesh(newList: List<Book>) {
        listOfBooks = arrayListOf()
        listOfBooks.addAll(newList)
        notifyDataSetChanged()
    }
}

class BooksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemFavBinding.bind(view)

    fun bind(book: Book) {

        binding.titleSearch.text = book.volumeInfo?.title
        book.volumeInfo.authors?.let {
            binding.authorSearch.text = it[0]
        }

        book.volumeInfo.imageLinks.let {
            Glide.with(itemView.context)
                .load(it?.thumbnail)
                .placeholder(R.drawable.no_cover_thumb)
                .into(binding.imageViewBookFav)
        }
    }

}