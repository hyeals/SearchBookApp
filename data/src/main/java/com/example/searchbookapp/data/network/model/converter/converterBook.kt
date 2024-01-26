package com.example.searchbookapp.data.network.model.converter

import com.example.searchbookapp.data.network.model.response.BookDetailResponse
import com.example.searchbookapp.data.network.model.response.ThumbNailBook
import com.example.searchbookapp.domain.model.BookDetail
import com.example.searchbookapp.domain.model.ThumbnailBook

fun ThumbNailBook.toThumbnailBook(): ThumbnailBook = ThumbnailBook(
    title = this.title,
    subtitle = this.subtitle,
    isbn13 = this.isbn13,
    price = this.price,
    image = this.image
)

fun BookDetailResponse.toDetailBook(): BookDetail = BookDetail(
    title = this.title,
    subtitle = this.subtitle,
    isbn13 = this.isbn13,
    price = this.price,
    image = this.image,
    authors = this.authors,
    publisher = this.publisher,
    isbn10 = this.isbn10,
    pages = this.pages,
    year = this.year,
    rating = this.rating,
    desc = this.desc,
    url = this.url
)