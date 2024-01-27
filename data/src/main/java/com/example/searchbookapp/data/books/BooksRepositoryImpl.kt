package com.example.searchbookapp.data.books

import com.example.searchbookapp.domain.model.BaseBook
import com.example.searchbookapp.domain.model.BookDetail
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.repository.BookRepository
import javax.inject.Inject
class BooksRepositoryImpl @Inject constructor(
    private val remoteDataSource: BooksRemoteDataSource
): BookRepository {
    override suspend fun getThumbnailData(searchedInput: String): EntityWrapper<BaseBook> {
        return remoteDataSource.getThumbnailData(searchedInput)
    }

    override suspend fun getDetailBookData(isbn13: String): EntityWrapper<BookDetail> {
        return remoteDataSource.getDetailData(isbn13)
    }
}