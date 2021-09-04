package com.example.book.model

import com.google.gson.annotations.SerializedName

data class BookResponse(

    @SerializedName("items")
    val items : List<Book>
)

data class Book(

    @SerializedName("id")
    val id: String,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo,
    @SerializedName("publishedDate")
    val publishedDate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks?
)
data class VolumeInfo(

    @SerializedName("title")
    val title: String,
    @SerializedName("authors")
    val authors: List<String>
)

data class ImageLinks(

    @SerializedName("smallThumbnail")
    val smallThumbnail: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?
)