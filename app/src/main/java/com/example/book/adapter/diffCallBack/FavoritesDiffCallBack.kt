package com.example.book.adapter.diffCallBack
import androidx.recyclerview.widget.DiffUtil
import com.example.book.model.Book

open class FavoritesDiffCallBack : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }

}