package com.example.searchbookapp.data.network.model.mapper

import com.example.searchbookapp.data.network.model.converter.toDetailBook
import com.example.searchbookapp.data.network.model.response.BookDetailResponse
import com.example.searchbookapp.domain.model.BookDetail
import com.example.searchbookapp.domain.model.EntityWrapper
import javax.inject.Inject

class BookDetailMapper @Inject constructor()
    : BaseMapper<BookDetailResponse, BookDetail>() {
    override fun getSuccess(
        model: BookDetailResponse?,
        extra: Any?
    ): EntityWrapper.Success<BookDetail> {
        return model?.let {
            EntityWrapper.Success(
                entity = model.toDetailBook()
            )
        } ?: EntityWrapper.Success(
            entity = BookDetail()
        )
    }

    override fun getFailure(error: Throwable): EntityWrapper.Fail<BookDetail> {
        return EntityWrapper.Fail(error)
    }
}