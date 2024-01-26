package com.example.searchbookapp.data.network.model.response

import com.google.gson.annotations.SerializedName

data class BookDetailResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("isbn13")
    val isbn13: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("authors")
    val authors: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("isbn10")
    val isbn10: String,
    @SerializedName("pages")
    val pages: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("url")
    val url: String
    // Todo: Pdf 처리
)