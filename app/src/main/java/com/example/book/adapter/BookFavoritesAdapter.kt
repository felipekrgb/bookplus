package com.example.book.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.book.R
import com.example.book.adapter.diffCallBack.FavoritesDiffCallBack
import com.example.book.databinding.ItemFavBinding
import com.example.book.model.Book

class BookFavoritesAdapter : ListAdapter<Book, BooksViewHolder>(FavoritesDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_fav, parent, false).let {
            return BooksViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
    }
}

class BooksViewHolder (view: View): RecyclerView.ViewHolder(view){

    private val binding = ItemFavBinding.bind(view)

    fun bind(book: Book) {
        book.volumeInfo.imageLinks.let {
            Glide.with(itemView.context)
                .load(it?.thumbnail)
                .placeholder(R.drawable.no_cover_thumb)
                .into(binding.imageViewBookFav)
        }

    }

}