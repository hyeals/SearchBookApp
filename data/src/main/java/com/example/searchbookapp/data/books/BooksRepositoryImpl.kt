package com.example.searchbookapp.data.books

import com.example.searchbookapp.domain.model.BaseBook
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.model.ThumbnailBook
import com.example.searchbookapp.domain.repository.BookRepository
import javax.inject.Inject
class BooksRepositoryImpl @Inject constructor(
    private val remoteDataSource: BooksRemoteDataSource
): BookRepository {
    override suspend fun getThumbnailData(searchedInput: String): EntityWrapper<BaseBook> {
        return remoteDataSource.getThumbnailData(searchedInput)
    }
}