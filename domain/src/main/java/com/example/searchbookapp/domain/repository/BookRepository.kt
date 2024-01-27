package com.example.searchbookapp.domain.repository

import androidx.paging.PagingData
import com.example.searchbookapp.domain.model.BookDetail
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.model.ThumbnailBook
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getThumbnailData(searchedInput: String, page: String): Flow<PagingData<ThumbnailBook>>

    suspend fun getDetailBookData(isbn13: String): EntityWrapper<BookDetail>
}