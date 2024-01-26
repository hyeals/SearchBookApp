package com.example.searchbookapp.domain.model

import java.io.Serializable

data class BaseBook(
    val total: String = "",
    val page: String = "",
    val books: List<ThumbnailBook> = listOf(),
)