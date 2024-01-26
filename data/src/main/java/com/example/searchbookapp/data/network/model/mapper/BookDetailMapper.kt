package com.example.searchbookapp.data.network.model.mapper

import com.example.searchbookapp.data.network.model.converter.toDetailBook
import com.example.searchbookapp.data.network.model.response.BookDetailResponse
import com.example.searchbookapp.domain.model.BookDetail
import com.example.searchbookapp.domain.model.EntityWrapper
import javax.inject.Inject

class BookDetailMapper @Inject constructor()
    : BaseMapper<List<BookDetailResponse>, List<BookDetail>>() {
    override fun getSuccess(
        model: List<BookDetailResponse>?,
        extra: Any?
    ): EntityWrapper.Success<List<BookDetail>> {
        return model?.let {
            EntityWrapper.Success(
                entity = mutableListOf<BookDetail>()
                    .apply {
                        addAll(model.map { it.toDetailBook() })
                    }
            )
        } ?: EntityWrapper.Success(
            entity = listOf()
        )
    }

    override fun getFailure(error: Throwable): EntityWrapper.Fail<List<BookDetail>> {
        return EntityWrapper.Fail(error)
    }
}