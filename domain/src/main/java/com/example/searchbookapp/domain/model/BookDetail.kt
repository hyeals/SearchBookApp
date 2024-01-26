package com.example.searchbookapp.domain.model

data class BookDetail(
    override val title: String = "",
    override val subtitle: String = "",
    override val isbn13: String = "",
    override val price: String = "",
    override val image: String = "",
    val authors: String = "",
    val publisher: String = "",
    val isbn10: String = "",
    val pages: String = "",
    val year: String = "",
    val rating: String = "",
    val desc: String = "",
    val url: String = ""
    // todo: Pdf 처리
): ThumbnailBook()
