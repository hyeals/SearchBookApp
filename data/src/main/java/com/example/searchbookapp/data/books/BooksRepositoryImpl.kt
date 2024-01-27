package com.example.searchbookapp.data.books

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.searchbookapp.domain.model.BaseBook
import com.example.searchbookapp.domain.model.BookDetail
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.model.ThumbnailBook
import com.example.searchbookapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class BooksRepositoryImpl @Inject constructor(
    private val remoteDataSource: BooksRemoteDataSource
): BookRepository {
    override suspend fun getThumbnailData(searchedInput: String, page: String): Flow<PagingData<ThumbnailBook>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = {
                BookPagingDataSource(remoteDataSource, searchedInput)
            }
        ).flow
    }

    override suspend fun getDetailBookData(isbn13: String): EntityWrapper<BookDetail> {
        return remoteDataSource.getDetailData(isbn13)
    }
}