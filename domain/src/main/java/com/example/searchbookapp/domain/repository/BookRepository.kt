package com.example.searchbookapp.domain.repository

import com.example.searchbookapp.domain.model.BaseBook
import com.example.searchbookapp.domain.model.BookDetail
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.model.ThumbnailBook

interface BookRepository {
    suspend fun getThumbnailData(searchedInput: String): EntityWrapper<BaseBook>

    suspend fun getDetailBookData(isbn13: String): EntityWrapper<BookDetail>
}