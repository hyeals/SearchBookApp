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
    /**
     * 책 목록을 조회합니다.
     * @param searchedInput 조회할 책 목록
     * @return [EntityWrapper<BaseBook>] 조회된 책 정보 목록
     */
    suspend fun getThumbnailData(searchedInput: String): EntityWrapper<BaseBook>

    /**
     * 책 상세 정보를 조회합니다.
     * @param isbn13 조회할 책 isbn13
     * @return [EntityWrapper<BookDetail>] 책 상세 정보 목록
     */
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