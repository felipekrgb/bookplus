package com.example.book.model

import java.io.Serializable

data class BookResponse(

    val items: List<Book>?
) : Serializable

data class Book(

    val id: String,
    val volumeInfo: VolumeInfo,
) : Serializable

data class VolumeInfo(

    val title: String?,
    val authors: List<String>?,
    val description: String?,
    val publishedDate: String?,
    val pageCount: Int?,
    val imageLinks: ImageLinks?
) : Serializable

data class ImageLinks(

    val smallThumbnail: String?,
    val thumbnail: String?
) : Serializable