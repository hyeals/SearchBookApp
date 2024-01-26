package com.example.searchbookapp.data.network.model.response

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("total")
    val total: String,
    @SerializedName("page")
    val page: String,
    @SerializedName("books")
    val books: List<ThumbNailBook>
)

data class ThumbNailBook(
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("isbn13")
    val isbn13: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("image")
    val image: String
)