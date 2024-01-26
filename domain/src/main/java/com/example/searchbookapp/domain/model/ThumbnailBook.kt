package com.example.searchbookapp.domain.model

import java.io.Serializable

open class ThumbnailBook(
    open val title: String = "",
    open val subtitle: String = "",
    open val isbn13: String = "",
    open val price: String = "",
    open val image: String = ""
): Serializable