package com.example.searchbookapp.data.books

import com.example.searchbookapp.data.network.api.NetworkRequestFactory
import com.example.searchbookapp.data.network.model.mapper.BookDetailMapper
import com.example.searchbookapp.data.network.model.mapper.BookMapper
import com.example.searchbookapp.data.network.model.response.BookDetailResponse
import com.example.searchbookapp.data.network.model.response.BookResponse
import com.example.searchbookapp.domain.model.BaseBook
import com.example.searchbookapp.domain.model.BookDetail
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.model.ThumbnailBook
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

interface BooksRemoteDataSource {
    // todo: 책 목록 가져오는 api
    suspend fun getThumbnailData(searchedInput: String): EntityWrapper<BaseBook>

    suspend fun getDetailData(isbn13: String): EntityWrapper<BookDetail>
}

class BooksRemoteDataSourceImpl @Inject constructor(
    private val networkRequestFactory: NetworkRequestFactory
): BooksRemoteDataSource{
    override suspend fun getThumbnailData(searchedInput: String): EntityWrapper<BaseBook> {
        return BookMapper().mapFromResult(
            result = networkRequestFactory.create(
                url = searchedInput,
                type = object : TypeToken<BookResponse>(){}.type
            )
        )
    }

    override suspend fun getDetailData(isbn13: String): EntityWrapper<BookDetail> {
        return BookDetailMapper().mapFromResult(
            result = networkRequestFactory.create(
                url = "books/${isbn13}",
                type = object : TypeToken<BookDetailResponse>(){}.type
            )
        )
    }
}